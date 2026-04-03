import sys
from lexer import Lexer
from parser import Parser
from interpreter import Interpreter


def run(text):
    lexer = Lexer(text)
    parser = Parser(lexer)
    interpreter = Interpreter(parser)
    result = interpreter.interpret()
    print(result)


def main():
    if len(sys.argv) > 1:
        with open(sys.argv[1]) as f:
            text = f.read()
        run(text)
    else:
        print('Usage: python3 src/main.py <filename>')
        print('Example: python3 src/main.py examples/section9.pas')


if __name__ == '__main__':
    main()
