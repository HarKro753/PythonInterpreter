import sys
from lexer import Lexer
from interpreter import Interpreter


def run(text):
    lexer = Lexer(text)
    interpreter = Interpreter(lexer)
    result = interpreter.expr()
    print(result)


def main():
    if len(sys.argv) > 1:
        with open(sys.argv[1]) as f:
            for line in f:
                line = line.strip()
                if line:
                    run(line)
    else:
        while True:
            try:
                text = input('calc> ')
            except EOFError:
                break
            if not text:
                continue
            run(text)


if __name__ == '__main__':
    main()
