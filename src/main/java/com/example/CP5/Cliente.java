package com.example.CP5;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Cliente {

    public static void main(String[] args) {
        BigInteger p = new BigInteger("83");
        BigInteger q = new BigInteger("97");

        RSA rsaClient = new RSA(p, q);
        System.out.println("--- CONFIGURAÇÃO RSA DO CLIENTE ---");
        System.out.println(rsaClient);

        try (Socket socket = new Socket("localhost", 12345)) {
            System.out.println("\n[CLIENTE] Conectado ao servidor.");

            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            Conexao.enviar(out, rsaClient.getPublicKeyE().toString());
            Conexao.enviar(out, rsaClient.getN().toString());
            BigInteger serverE = new BigInteger(Conexao.receber(in));
            BigInteger serverN = new BigInteger(Conexao.receber(in));
            System.out.println("--- TROCA DE CHAVES CONCLUÍDA ---\n");

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("[CLIENTE - Digite sua mensagem]: ");
                String clientMsg = scanner.nextLine();
                byte[] clientMsgBytes = clientMsg.getBytes(StandardCharsets.UTF_8);

                StringBuilder encryptedMsgBuilder = new StringBuilder();
                for (byte b : clientMsgBytes) {
                    BigInteger encryptedByte = rsaClient.encrypt(new byte[]{b}, serverE, serverN);
                    encryptedMsgBuilder.append(encryptedByte).append(" ");
                }

                Conexao.enviar(out, encryptedMsgBuilder.toString().trim());
                System.out.println("[CLIENTE - Criptografado e enviado]");

                if (clientMsg.equalsIgnoreCase("exit")) break;

                String receivedData = Conexao.receber(in);
                System.out.println("\n[SERVIDOR - Criptografado]: " + receivedData);

                String[] encryptedBytesStr = receivedData.trim().split(" ");
                byte[] decryptedBytes = new byte[encryptedBytesStr.length];

                for (int i = 0; i < encryptedBytesStr.length; i++) {
                    BigInteger encryptedByte = new BigInteger(encryptedBytesStr[i]);
                    byte[] decryptedByte = rsaClient.decrypt(encryptedByte);
                    decryptedBytes[i] = decryptedByte[0];
                }

                String decryptedServerMsg = new String(decryptedBytes, StandardCharsets.UTF_8);
                System.out.println("[SERVIDOR - Descriptografado]: " + decryptedServerMsg);

                if (decryptedServerMsg.equalsIgnoreCase("exit")) break;
            }

            socket.close();
            System.out.println("[CLIENTE] Conexão encerrada.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}