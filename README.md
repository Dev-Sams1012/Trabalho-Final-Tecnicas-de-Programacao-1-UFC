# 👕 GVP – Gestor de Vestuário Pessoal

[![Java](https://img.shields.io/badge/Java-17-blue?logo=java)](https://www.java.com)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
![Status](https://img.shields.io/badge/status-em%20desenvolvimento-orange)
![Platform](https://img.shields.io/badge/plataforma-desktop-lightgrey)

> Aplicativo Java para controle de vestuário pessoal, uso de looks, lavagens e empréstimos, com futura interface gráfica.

---

## ✨ Funcionalidades

- 📦 Cadastro de peças de roupas e acessórios  
- 🎨 Classificação por cor, loja, estado de conservação e parte do corpo  
- 👗 Montagem de looks personalizados, com validação automática  
- 📆 Registro de uso (data, ocasião)  
- 🧼 Lavagem de roupas com controle de lavagens realizadas  
- 📤 Empréstimo e devolução de peças específicas  
- 💾 Persistência de dados (futuro)  
- 🖼️ Interface gráfica (em breve)  

---

## 🧱 Estrutura do Projeto

```bash
src/ufc/dc/tp1/app/
├── itens/
│   ├── Item.java              # Classe abstrata base
│   ├── Look.java              # Gerencia conjuntos de roupas (looks)
│   ├── Lavagem.java           # Registro de lavagens
│   ├── interfaces/            # Interfaces para comportamentos especiais
│   │   ├── ILavavel.java
│   │   └── IEmprestavel.java
│   ├── vestuario/             # Subclasses de vestuário
│       ├── Camisa.java
│       ├── Calca.java
│       ├── Casaco.java
│       ├── Bone.java
│       ├── Relogio.java
│       └── Etc...
├── enums/
│   ├── Conservacao.java       # Estado de conservação do item
│   ├── Tamanho.java           # Tamanho das roupas
│   └── CategoriaRoupa.java    # Parte do corpo correspondente
├── main/
│   └── Main.java              # Classe principal para testes

```
---

## 🧪 Exemplo de Uso

```java
Camisa camisa = new Camisa("camisa_red", "red", "Nike", Conservação.BOA, Tamanho.M);
Calça calca = new Calça("calca_blue", "blue", "Adidas", Conservação.EXCELENTE, 42);

Look look = new Look();
look.montarLook(camisa, calca);
look.registrarUso("10/12/2024", "Evento UFC");

System.out.println(look);
```

---

## 🛣️ Roadmap

- [x] Modelagem orientada a objetos
- [x] Interfaces `ILavavel`, `IEmprestavel`
- [x] Subclasses de vestuário com polimorfismo
- [x] Registro de utilização, lavagem e empréstimo
- [ ] Persistência de dados em JSON
- [ ] GUI em Swing ou JavaFX
- [ ] Testes automatizados com JUnit

---

## 🛠️ Tecnologias

- Java 17+
- Eclipse IDE
- Paradigma Orientado a Objetos
- (Futuro) JSON para persistência
- (Futuro) Swing ou JavaFX para GUI

---

## 🤝 Contribuições

Contribuições são bem-vindas!

Fork este repositório

Crie uma branch:

```bash
git checkout -b minha-feature
```

Commit suas mudanças:

```bash
git commit -m 'feat: nova funcionalidade'
```

Push para o branch:

```bash
git push origin minha-feature
```

Abra um Pull Request

---

## 🧑‍💻 Autor

Samuel Augusto  
Estudante de Ciência da Computação – UFC  
GitHub: [Dev-Sams1012](https://github.com/Dev-Sams1012)
