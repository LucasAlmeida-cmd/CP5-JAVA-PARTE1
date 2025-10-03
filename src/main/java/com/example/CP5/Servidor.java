package com.example.CP5;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Servidor {

    public static void main(String[] args) {
        BigInteger p = new BigInteger("61");
        BigInteger q = new BigInteger("53");

        RSA rsaServer = new RSA(p, q);
        System.out.println("--- CONFIGURAÇÃO RSA DO SERVIDOR ---");
        System.out.println(rsaServer);

        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("\n[SERVIDOR] Aguardando conexão do cliente na porta 12345...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("[SERVIDOR] Cliente conectado: " + clientSocket.getInetAddress().getHostAddress());

            DataInputStream in = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());


            BigInteger clientE = new BigInteger(Conexao.receber(in));
            BigInteger clientN = new BigInteger(Conexao.receber(in));
            Conexao.enviar(out, rsaServer.getPublicKeyE().toString());
            Conexao.enviar(out, rsaServer.getN().toString());
            System.out.println("--- TROCA DE CHAVES CONCLUÍDA ---\n");

            Scanner scanner = new Scanner(System.in);
            while (true) {
                String receivedData = Conexao.receber(in);
                System.out.println("\n[CLIENTE - Criptografado]: " + receivedData);

                String[] encryptedBytesStr = receivedData.trim().split(" ");
                byte[] decryptedBytes = new byte[encryptedBytesStr.length];

                for (int i = 0; i < encryptedBytesStr.length; i++) {
                    BigInteger encryptedByte = new BigInteger(encryptedBytesStr[i]);
                    byte[] decryptedByte = rsaServer.decrypt(encryptedByte);
                    decryptedBytes[i] = decryptedByte[0];
                }

                String decryptedMsg = new String(decryptedBytes, StandardCharsets.UTF_8);
                System.out.println("[CLIENTE - Descriptografado]: " + decryptedMsg);

                if (decryptedMsg.equalsIgnoreCase("exit")) break;

                System.out.print("[SERVIDOR - Digite sua mensagem]: ");
                String serverMsg = scanner.nextLine();
                byte[] serverMsgBytes = serverMsg.getBytes(StandardCharsets.UTF_8);

                StringBuilder encryptedMsgBuilder = new StringBuilder();
                for (byte b : serverMsgBytes) {
                    BigInteger encryptedByte = rsaServer.encrypt(new byte[]{b}, clientE, clientN);
                    encryptedMsgBuilder.append(encryptedByte).append(" ");
                }

                Conexao.enviar(out, encryptedMsgBuilder.toString().trim());
                System.out.println("[SERVIDOR - Criptografado e enviado]");

                if (serverMsg.equalsIgnoreCase("exit")) break;
            }

            clientSocket.close();
            System.out.println("[SERVIDOR] Conexão encerrada.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}