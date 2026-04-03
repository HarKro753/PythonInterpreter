<a id="readme-top"></a>

<!-- PROJECT SHIELDS -->

[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]

<!-- PROJECT LOGO -->
<br />
<div align="center">
  <h1>Python Interpreter</h1>

  <p align="center">
    A simple interpreter built from scratch in Python, following Ruslan Spivak's "Let's Build A Simple Interpreter" series.
    <br />
    <a href="https://ruslanspivak.com/lsbasi-part1/"><strong>Follow the tutorial »</strong></a>
    <br />
    <br />
    <a href="#usage">View Demo</a>
    &middot;
    <a href="https://github.com/harrokrog/PythonInterpreter/issues">Report Bug</a>
    &middot;
    <a href="https://github.com/harrokrog/PythonInterpreter/issues">Request Feature</a>
  </p>
</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->

## About The Project

This project is a hand-built interpreter written in Python. It follows the ["Let's Build A Simple Interpreter"](https://ruslanspivak.com/lsbasi-part1/) tutorial series by Ruslan Spivak to understand how programming languages and interpreters work under the hood.

**Currently implemented (Part 1):**

- Lexer that tokenizes input into `INTEGER`, `PLUS`, and `EOF` tokens
- Parser that validates the `INTEGER + INTEGER` grammar
- Evaluator that computes the result
- Support for running `.calc` files as a custom file format

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Built With

[![Python][Python-shield]][Python-url]

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- GETTING STARTED -->

## Getting Started

To get a local copy up and running, follow these steps.

### Prerequisites

- Python 3.6 or higher
  ```sh
  python3 --version
  ```

### Installation

1. Clone the repo
   ```sh
   git clone https://github.com/harrokrog/PythonInterpreter.git
   ```
2. Navigate to the project directory
   ```sh
   cd PythonInterpreter
   ```

That's it — no dependencies required.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- USAGE -->

## Usage

### Interactive Mode

Launch the interpreter in interactive (REPL) mode:

```sh
python3 interpreter.py
```

```
calc> 3+5
8
calc> 1+2
3
```

### File Mode

Create a `.calc` file with one expression per line:

```
3+5
1+2
7+8
```

Run it:

```sh
python3 interpreter.py examplse/section1.calc
```

```
8
3
15
```

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- ROADMAP -->

## Roadmap

- [x] Part 1 — Single-digit addition
- [ ] Part 2 — Multi-digit integers, subtraction, whitespace
- [ ] Part 3 — Syntax diagrams, arbitrary-length +/- expressions
- [ ] Part 4 — Context-free grammars, multiplication and division
- [ ] Part 5 — Operator precedence and associativity
- [ ] Part 6 — Parenthesized expressions
- [ ] Part 7 — Abstract Syntax Trees (ASTs)
- [ ] Part 8 — Unary operators
- [ ] Part 9 — Pascal compound statements, variables
- [ ] Part 10 — Complete Pascal programs
- [ ] Part 11 — Symbol table management
- [ ] Part 12 — Procedure declarations
- [ ] Part 13 — Semantic analysis
- [ ] Part 14 — Nested scopes, source-to-source compiler
- [ ] Part 15 — Improved error reporting
- [ ] Part 16 — Recognizing procedure calls
- [ ] Part 17 — Call stack and activation records
- [ ] Part 18 — Executing procedure calls

See the [tutorial series](https://ruslanspivak.com/lsbasi-part1/) for the full plan.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- CONTRIBUTING -->

## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- LICENSE -->

## License

Distributed under the MIT License. See `LICENSE` for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- CONTACT -->

## Contact

Project Link: [https://github.com/harrokrog/PythonInterpreter](https://github.com/harrokrog/PythonInterpreter)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- ACKNOWLEDGMENTS -->

## Acknowledgments

- [Let's Build A Simple Interpreter — Ruslan Spivak](https://ruslanspivak.com/lsbasi-part1/)
- [Best-README-Template](https://github.com/othneildrew/Best-README-Template)
- [Shields.io](https://shields.io)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- MARKDOWN LINKS & IMAGES -->

[contributors-shield]: https://img.shields.io/github/contributors/harrokrog/PythonInterpreter.svg?style=for-the-badge
[contributors-url]: https://github.com/harrokrog/PythonInterpreter/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/harrokrog/PythonInterpreter.svg?style=for-the-badge
[forks-url]: https://github.com/harrokrog/PythonInterpreter/network/members
[stars-shield]: https://img.shields.io/github/stars/harrokrog/PythonInterpreter.svg?style=for-the-badge
[stars-url]: https://github.com/harrokrog/PythonInterpreter/stargazers
[issues-shield]: https://img.shields.io/github/issues/harrokrog/PythonInterpreter.svg?style=for-the-badge
[issues-url]: https://github.com/harrokrog/PythonInterpreter/issues
[license-shield]: https://img.shields.io/github/license/harrokrog/PythonInterpreter.svg?style=for-the-badge
[license-url]: https://github.com/harrokrog/PythonInterpreter/blob/main/LICENSE
[Python-shield]: https://img.shields.io/badge/Python-3776AB?style=for-the-badge&logo=python&logoColor=white
[Python-url]: https://python.org
