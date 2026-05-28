# Exemplo prático de cofre de segurança

Nesse exemplo prático, iremos usar a API do Gemini, com o HashiVault, para interagir com uma aplicação que responde a e-mails usando IA.
Iremos demonstrar passo a passo como usar cofres de senha, na prática, como alternativa a arquivos .env ou variáveis expostas, práticas consideradas inseguras.

---
## Passo 1: Subir o container do vault

Primeiro, iremos subir o container contendo o HashiVault.

Dentro da raíz do projeto, em um terminal, execute o comando:

```bash
docker-compose up -d
```

Isso irá subir o cofre de senhas.

No navegador, você pode acessar a interface gráfica do HashiVault [nesse link](http://127.0.0.1:8200).

```
http://127.0.0.1:8200
```

E use o vault token para se autenticar. Na interface Web, procure o seguinte campo:

```
Method: Token | Token: 3c9fd6be-7bc2-9d1f-4fb3-cd745c0fc4e8
```

---
## Passo 2: Obter chave de API do Gemini e senha de aplicativo do Google

No navegador, acesse o [Google AI Studio](https://makersuite.google.com/) e gere uma chave de API. Caso não consiga gerar usando a sua conta institucional, tente gerar com uma conta pessoal. Armazene essa chave em um **local seguro e estritamente confidencial**, garantindo que **apenas você** tenha acesso a ela.

No seu celular ou navegador, faça login na sua conta do[ Google](https://myaccount.google.com/u/2/apppasswords?) e crie uma senha de aplicativo. Assim como no passo anterior, **salve essa senha em um ambiente seguro e privado**, tomando cuidado para não expô-la a terceiros.

---
## Passo 3: Guardar segredos no cofre (chave de API e senha de app)

Acesse o container que está executando o HashiVault:

```bash
docker exec -it vault-2.0 sh
```

E depois, execute os seguintes comandos, substituindo os valores dos campos:

```bash
export VAULT_ADDR="http://127.0.0.1:8200"

export VAULT_TOKEN="3c9fd6be-7bc2-9d1f-4fb3-cd745c0fc4e8"

vault kv put secret/gerador-resposta email.usuario="seuemail@gmail.com" email.senha="senha-app-aqui" gemini.api.key="Chave-Api-Aqui"
```

Você verá no terminal uma saída parecida com essa:

```
======= Metadata =======
Key                Value
---                -----
created_time       2026-05-27T03:48:11.780602162Z
custom_metadata    <nil>
deletion_time      n/a
destroyed          false
version            1
```

Depois saia do container, usando o atalho ctrl+d.

---
## Passo 4: Executar a classe Main


Antes de executar, recomendamos que você envie um e-mail de teste de outro endereço para o e-mail que está configurado na aplicação. 
Isso garantirá que há mensagens disponíveis para responder.

Usando uma IDE, como o IntelliJ, execute a classe Main em _/src/main/java/org.application_.

Se todos os passos anteriores tiverem sido executados corretamente, você verá algo assim:

```bash
--- E-MAILS DISPONÍVEIS PARA RESPONDER ---
[0] De: Exemplo <endereco-exemplo-gmail.com> | Assunto: Exemplo
[1] De: Exemplo <endereco-exemplo-gmail.com> | Assunto: Exemplo
[2] De: Exemplo <endereco-exemplo-gmail.com> | Assunto: Exemplo
[3] De: Exemplo <endereco-exemplo-gmail.com> | Assunto: Exemplo
[4] De: Exemplo <endereco-exemplo-gmail.com> | Assunto: Exemplo
[5] De: Exemplo <endereco-exemplo-gmail.com> | Assunto: Exemplo
[6] De: Exemplo <endereco-exemplo-gmail.com> | Assunto: Exemplo
[7] De: Exemplo <endereco-exemplo-gmail.com> | Assunto: Exemplo
[8] De: Exemplo <endereco-exemplo-gmail.com> | Assunto: Exemplo
[9] De: Exemplo <endereco-exemplo-gmail.com> | Assunto: Exemplo
```

Escolha um número de 0-9 para responder um e-mail de forma automática.
