# Chat TCP com Criptografia RSA

## Descrição do Projeto

Este projeto é uma implementação de um sistema de chat cliente-servidor seguro, desenvolvido como parte do **Checkpoint 5**. A comunicação entre o cliente e o servidor é estabelecida via Sockets TCP, e todas as mensagens trocadas são protegidas com criptografia de ponta a ponta utilizando o algoritmo RSA.


## Funcionalidades

-   **Comunicação Cliente-Servidor:** Estabelece uma conexão estável usando o protocolo TCP.
-   **Criptografia RSA:** Cada mensagem é criptografada byte a byte antes de ser enviada pela rede.
-   **Troca de Chaves Públicas:** No início da conexão, o cliente e o servidor trocam suas chaves públicas para permitir a criptografia mútua.
-   **Comunicação Bidirecional:** Após a conexão, ambos podem enviar e receber mensagens em tempo real.
-   **Estrutura Modular:** O código é organizado com uma classe `Conexao` dedicada para abstrair as operações de rede, e uma classe `RSA` para a lógica de criptografia.

## Tecnologias Utilizadas

-   **Linguagem:** Java (JDK 11 ou superior)
-   **Redes:** API Java Sockets (TCP/IP)
-   **Criptografia:** Java `BigInteger` para implementar a matemática do algoritmo RSA.

## Como o Projeto Funciona

A lógica do projeto é dividida em três etapas principais: inicialização, comunicação e encerramento.

### 1. O Algoritmo RSA

O RSA é um algoritmo de chave assimétrica. Cada participante (cliente e servidor) gera seu próprio par de chaves: uma pública e uma privada.

-   **Chave Pública (`e`, `n`):** Usada para criptografar mensagens. Pode ser compartilhada livremente.
-   **Chave Privada (`d`, `n`):** Usada para descriptografar mensagens. Deve ser mantida em segredo.

As chaves são geradas a partir de dois números primos grandes (`p` e `q`) escolhidos pelo grupo.

### 2. Fluxo de Comunicação

1.  **Inicialização:** O Servidor é iniciado e aguarda uma conexão. O Cliente e o Servidor, de forma independente, geram seus próprios pares de chaves RSA.
2.  **Conexão:** O Cliente se conecta ao endereço IP e porta do Servidor.
3.  **Troca de Chaves:** Logo após a conexão, o Cliente envia sua chave pública para o Servidor. O Servidor, por sua vez, envia sua chave pública para o Cliente. Agora, ambos conhecem a chave pública um do outro.
4.  **Comunicação Criptografada:**
    -   Quando o **Cliente** quer enviar uma mensagem, ele a quebra em bytes. Cada byte é criptografado usando a **chave pública do Servidor**.
    -   Quando o **Servidor** recebe os dados, ele usa sua própria **chave privada** para descriptografar cada byte e remontar a mensagem original.
    -   O processo inverso ocorre quando o Servidor responde.
5.  **Desconexão:** A conexão é encerrada de forma limpa quando uma das partes envia a mensagem "exit".

## Como Executar o Projeto

Siga os passos abaixo para compilar e executar a aplicação.

### Pré-requisitos

-   Java Development Kit (JDK) instalado.

### Passos

1.  **Clone ou baixe o repositório.**

2.  **Abra o terminal** na pasta raiz do projeto.

3.  **Compile todos os arquivos Java:** Inicio o projeto.

4.  **Comece a conversar!** Digite mensagens em qualquer um dos terminais e pressione Enter.

## Exemplo de Uso

Cliente enviando mensagem e Servidor recebendo:
<img width="1714" height="718" alt="image" src="https://github.com/user-attachments/assets/293be4ed-ccd2-44d0-9684-c994ce6ff16f" />

Servidor enviando a mensagem e Cliente recebendo:
<img width="1713" height="719" alt="image" src="https://github.com/user-attachments/assets/3b64e78d-d05c-44ec-ba8b-e1faae848469" />



## Teste de Validação com Ferramenta Externa

Conforme solicitado, o funcionamento do algoritmo foi validado usando a calculadora **RSA Express Encryption-Decryption Calculator** da Drexel University.

**Link:** [https://www.cs.drexel.edu/~jpopyack/Courses/CSP/Fa17/notes/10.1_Cryptography/RSA_Express_EncryptDecrypt.html](https://www.cs.drexel.edu/~jpopyack/Courses/CSP/Fa17/notes/10.1_Cryptography/RSA_Express_EncryptDecrypt.html)

O teste consiste em criptografar um único byte (representando um caractere) com a chave pública do servidor no site e verificar se o resultado bate com o valor criptografado exibido no console do programa.

Como os valores do Servidor:

<img width="1303" height="603" alt="image" src="https://github.com/user-attachments/assets/6b741ecc-3a9c-4915-9617-fbbcff574494" />

Como os valores do Cliente:

<img width="1270" height="594" alt="image" src="https://github.com/user-attachments/assets/a7a50963-26a3-49d2-915e-71d1ba046517" />

---

## Integrantes do Grupo

-   **Arthur Eduardo Luna Pulini** - RM: **557569**
-   **Lucas Almeida Fernandes de Moraes** - RM: **557569**
-   **Victor Nascimento Cosme** - RM: **558856**

## IDE Utilizada

O projeto foi desenvolvido utilizando a IDE: **IntelliJ IDEA**.
