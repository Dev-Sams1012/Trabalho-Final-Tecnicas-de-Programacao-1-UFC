# 🧥 Gestor de Vestuário Pessoal (GVP)

### Trabalho Final – Técnicas de Programação I  
**Autor:** Samuel Augusto de Abreu  
**Disciplina:** Técnicas de Programação I  
**Instituição:** Universidade Federal do Ceará (UFC)

---

## 📝 Descrição

O **Gestor de Vestuário Pessoal (GVP)** é uma aplicação desenvolvida em **Java**, utilizando **Swing** para interface gráfica, cujo objetivo é permitir o **gerenciamento de um acervo de vestimentas**, possibilitando **empréstimos, lavagens e composição de looks**.

O sistema foi desenvolvido como **trabalho final da disciplina de Técnicas de Programação I**, integrando diversos conceitos fundamentais da linguagem Java, como:

- Programação orientada a objetos (POO)  
- Herança, interfaces e polimorfismo  
- Manipulação de arquivos (serialização)  
- Tratamento de exceções personalizadas  
- Desenvolvimento de GUI com `JFrame`, `JPanel` e `JTabbedPane`

---

## 🎯 Objetivos do Projeto

- Criar um sistema de **cadastro e controle de vestimentas**.  
- Permitir **registro e devolução de empréstimos** de itens.  
- Controlar **lavagens** e o estado de conservação das roupas.  
- Oferecer uma interface intuitiva para **organização de looks completos**.  
- Demonstrar domínio dos princípios de **modularidade e boas práticas de programação**.

---

## 🧩 Estrutura do Projeto

```markdown
Trabalho_Final_TP1_UFC/
│
├── src/
│ └── ufc/dc/tp1/app/
│ ├── exceptions/ # Exceções personalizadas
│ ├── gui/ # Interface gráfica (Swing)
│ ├── itens/ # Classes principais do domínio
│ │ ├── enums/ # Enumerações auxiliares
│ │ └── vestuario/ # Subclasses de vestimentas (sem acento)
│
├── bin/ # Classes compiladas (.class)
├── .vscode/ # Configurações de IDE
├── .classpath / .project # Metadados do Eclipse
└── README.md # Este arquivo
```

---

### Principais Classes

- **`JanelaPrincipal.java`** → Cria a janela principal e organiza as abas (“Itens”, “Looks”, “Empréstimos”, “Lavagens”).  
- **`PainelItem.java`** → Responsável pelo cadastro e listagem de itens de vestuário.  
- **`PainelLook.java`** → Permite criar combinações de roupas (looks).  
- **`PainelEmprestimo.java`** → Gerencia empréstimos e devoluções, armazenando dados em arquivo binário (`emprestimos.dat`).  
- **`PainelLavagem.java`** → Registra lavagens e o estado de conservação dos itens.  
- **`Item.java`, `Emprestimo.java`, `Lavagem.java`, `Look.java`** → Modelos principais da aplicação.  
- **Enums** como `Tamanho`, `CategoriaRoupa` e `Conservacao` auxiliam na categorização dos itens.  
- **Exceções personalizadas** como `VestimentaJaEmprestadoException` e `DevolucaoSemEmprestimoException` tratam erros de lógica.

---

## 💾 Persistência de Dados

O sistema utiliza **serialização de objetos** para armazenar informações em arquivos locais:

- `itens.dat` — Itens cadastrados  
- `emprestimos.dat` — Registros de empréstimos  
- `lavagens.dat` — Histórico de lavagens  

Esses arquivos são gerados automaticamente no diretório do programa.

---

## 🧠 Conceitos Aplicados

- **POO:** abstração, herança, polimorfismo e encapsulamento  
- **Interfaces e enums**  
- **Tratamento de exceções personalizadas**  
- **Serialização de objetos** (`ObjectOutputStream`, `ObjectInputStream`)  
- **Manipulação de GUI com Swing** (layouts, eventos, diálogos, abas)  
- **Separação modular do código em pacotes**

---

## 🎨 Interface Gráfica

A interface foi construída com o **Swing**, contendo múltiplas abas que organizam as principais funcionalidades:

- 📦 **Itens** — cadastro e edição  
- 👗 **Looks** — montagem de combinações  
- 🔄 **Empréstimos** — controle de saída/devolução  
- 🧼 **Lavagens** — registro e histórico de manutenção


