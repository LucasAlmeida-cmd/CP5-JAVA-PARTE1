/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.CP5;

/**
 *
 * @author MSWagner
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.Scanner;

public class Server {
    Socket socketClient;
    ServerSocket serversocket;

    public boolean connect() {
        try {
            socketClient = serversocket.accept();  // fase de conexao
            return true;
        } catch (IOException e) {
            System.err.println("Nao fez conexao" + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        try {
            Server servidor = new Server();
            servidor.rodarServidor();
        } catch(Exception e){
            e.printStackTrace();
        }
    } 

    public void rodarServidor() throws Exception {
        String textoRecebido = "";
        String textoEnviado = "Olá, Cliente";
        String textoDecifrado;
        String textoCifrado;

        Scanner input = new Scanner(System.in);

        serversocket = new ServerSocket(9600);
        System.out.println("Servidor iniciado!");

        while(true) {
            if (connect()) {
                // Gerar chave pública e privada
                System.out.println("Gerando chave RSA...");
                KeyPair chaves = CriptografiaClienteServidor.gerarChavesPublicoPrivada();

                // enviar chave pública para o cliente
                System.out.println("Enviando chave pública...");
                Conexao.enviarChave(socketClient, chaves.getPublic());
         
                // receber chave pública do cliente
                System.out.println("Recebendo chave pública do cliente...");
                PublicKey chavePublica = Conexao.receberChave(socketClient);

                textoRecebido = Conexao.receber(socketClient);
                System.out.println(textoRecebido);
                // decifrar texto recebido do cliente
                textoDecifrado = CriptografiaClienteServidor.decifrar(textoRecebido, chaves.getPrivate());

                System.out.println("Cliente enviou: " + textoDecifrado);
                System.out.print("\nDigite a sua mensagem: ");// fase de dados
                textoEnviado = input.nextLine();

                // O texto é cifrado usando a chave pública recebida do cliente
                textoCifrado = CriptografiaClienteServidor.cifrar(textoEnviado, chavePublica);

                Conexao.enviar(socketClient, textoCifrado);
                socketClient.close();
            }
        }
    }

}
