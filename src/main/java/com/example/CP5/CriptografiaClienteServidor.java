/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.example.CP5;

/**
 *
 * @author MSWagner
 */

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class CriptografiaClienteServidor {
   public static KeyPair gerarChavesPublicoPrivada() throws NoSuchAlgorithmException{
      KeyPairGenerator geradorChave = KeyPairGenerator.getInstance("RSA");
      geradorChave.initialize(2048);
      KeyPair par = geradorChave.generateKeyPair();
      return par;
   }

   public static String 
         cifrar(String mensagem, PublicKey publicKey) throws Exception {

      byte[] messageToBytes = mensagem.getBytes();
      Cipher cifrador = Cipher.getInstance("RSA/ECB/PKCS1Padding");

      // Cifrar mensagem
      cifrador.init(Cipher.ENCRYPT_MODE, publicKey);
      byte[] bytesCripto = cifrador.doFinal(messageToBytes);

      return Base64.getEncoder().encodeToString(bytesCripto);
   }

   public static String 
         decifrar(String mensagem, PrivateKey privateKey) throws Exception {

      byte[] bytesCifrados = Base64.getDecoder().decode(mensagem);
      Cipher cifrador = Cipher.getInstance("RSA/ECB/PKCS1Padding");

      cifrador.init(Cipher.DECRYPT_MODE, privateKey);
      byte[] mensagemDecifrada = cifrador.doFinal(bytesCifrados);

      return new String(mensagemDecifrada, "UTF8");
   }

   public static PublicKey bytesParaChave(byte[] bytesChave) throws Exception {
      X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytesChave);
      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      return keyFactory.generatePublic(keySpec);
   }
}
