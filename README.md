<h1 align="center">DictionMaster</h1>

<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=21"><img src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat" border="0" alt="API"></a>
  <br>
  <a href="https://wa.me/+5513997254841"><img alt="WhatsApp" src="https://img.shields.io/badge/WhatsApp-25D366?style=for-the-badge&logo=whatsapp&logoColor=white"/></a>
  <a href="https://www.linkedin.com/in/christyan-segecs-359894115/"><img alt="Linkedin" src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white"/></a>
  <a href="mailto:christyansegecs@hotmail.com"><img alt="Gmail" src="https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white"/></a>
</p>

<p align="center">  

⭐ Esse é um projeto para demonstrar meu conhecimento técnico no desenvolvimento Android nativo com Kotlin. Mais informações técnicas abaixo.

 DictionMaster é um dicionário de inglês baseado na API
 gratuita Free Dictionary. A app apresenta um launch screen e na sequência chega à tela principal
 na qual você pode realizar uma busca no dicionário na língua apresentada (somente inglês). Ao
 realizar uma busca, uma tela com resultado é apresentada, nela você pode tocar o áudio do termo,
 ver os significados e para cada significado alguns exemplos. Cada usuário tem direito a 10 buscas
 gratuitas por dia (desconsiderando pesquisas já feitas), quando este limite é ultrapassado
 uma tela de compra é apresentada para ele. O fluxo feliz termina na exibição da tela de compra e o
 clicar na compra sai da tela. Um outro aspecto importante é que foi implementado um
 cache de busca, para que buscas iguais, pelo mesmo termo, não faça uma
 nova requisição REST do Free Dictionary API, puxando esses dados de um banco de dados local, salvos
 anteriormente com a primeira busca.
</p>

</br>

<p float="left" align="center">
<img alt="screenshot" width="30%" src="screenshots/screenshot-01.png"/>
 <img alt="screenshot" width="30%" src="screenshots/screenshot-02.png"/>
 <img alt="screenshot" width="30%" src="screenshots/screenshot-03.png"/>
 <img alt="screenshot" width="30%" src="screenshots/screenshot-04.png"/>
 <img alt="screenshot" width="30%" src="screenshots/screenshot-05.png"/>
</p>

## Download

Faça o download do Aplicativo clicando aqui >> <a href="apk/apk/DictionMaster.apk?raw=true">apk/DictionMaster APK</a>
</br>
Você pode ver <a href="https://www.google.com/search?q=como+instalar+um+apk+no+android">aqui</a> como instalar uma APK no seu aparelho android.

## Tecnologias usadas e bibliotecas de código aberto

- Minimum SDK level 24
- [Kotlin (Linguagem de Programação utilizada)](https://kotlinlang.org/)

- Jetpack
  - Lifecycle: Observe os ciclos de vida do Android e manipule os estados da interface do usuário após as alterações do ciclo de vida.
  - ViewModel: Gerencia o detentor de dados relacionados à interface do usuário e o ciclo de vida. Permite que os dados sobrevivam a alterações de configuração, como rotações de tela.
  - ViewBinding: Liga os componentes do XML no Kotlin através de uma classe que garante segurança de tipo e outras vantagens.
  - Room: Biblioteca de abstração do banco de dados SQLite que garante segurança em tempo de compilação e facilidade de uso.

- Arquitetura
  - MVVM (Model - View - ViewModel) + Clean Architecture
  - Comunicação da ViewModel com a View através de LiveData
  - Comunicação da ViewModel com a Model através de Kotlin Flow
  - Repositories para abstração da comunidação com a camada de dados.
  
- Bibliotecas
  - [Retrofit2 & OkHttp3](https://github.com/square/retrofit): Para realizar requisições seguindo o padrão HTTP.
  - [Room](https://developer.android.com/topic/libraries/architecture/room?hl=pt-br): Para criação e gerenciamento do banco de dados local. Obs.: Eu substitui o uso do banco de dados local por um banco de dados remoto (Firebase)

## Arquitetura
**DictionMaster** utiliza a arquitetura MVVM e o padrão de Repositories, que segue as [recomendações oficiais do Google](https://developer.android.com/topic/architecture).
<br>

## API de terceiros

Free Dictionary API - https://dictionaryapi.dev/
Uma API de Dicionário em Inglês.

# Licença

```xml

          Copyright [2023] [Christyan Segecs Silva]

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

```
