import sys
from lexer import Lexer
from parser import Parser
from semantic_analyzer import SemanticAnalyzer
from interpreter import Interpreter


def run(text):
    lexer = Lexer(text)
    parser = Parser(lexer)
    tree = parser.parse()

    semantic_analyzer = SemanticAnalyzer()
    semantic_analyzer.visit(tree)

    interpreter = Interpreter(tree)
    interpreter.interpret()
    print(interpreter.GLOBAL_SCOPE)


def main():
    if len(sys.argv) > 1:
        with open(sys.argv[1]) as f:
            text = f.read()
        run(text)
    else:
        print('Usage: python3 src/main.py <filename>')
        print('Example: python3 src/main.py examples/section18.pas')


if __name__ == '__main__':
    main()
