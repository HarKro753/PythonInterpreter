# Token types
INTEGER_CONST, REAL_CONST, PLUS, MINUS, MUL, FLOAT_DIV, INTEGER_DIV = (
    'INTEGER_CONST', 'REAL_CONST', 'PLUS', 'MINUS', 'MUL', 'FLOAT_DIV', 'INTEGER_DIV'
)
LPAREN, RPAREN, EOF = 'LPAREN', 'RPAREN', 'EOF'
BEGIN, END, DOT, ASSIGN, SEMI, ID = (
    'BEGIN', 'END', 'DOT', 'ASSIGN', 'SEMI', 'ID'
)
PROGRAM, VAR, PROCEDURE, INTEGER, REAL, COLON, COMMA = (
    'PROGRAM', 'VAR', 'PROCEDURE', 'INTEGER', 'REAL', 'COLON', 'COMMA'
)


class Token(object):
    def __init__(self, type, value, lineno=None, column=None):
        self.type = type
        self.value = value
        self.lineno = lineno
        self.column = column

    def __str__(self):
        return 'Token({type}, {value}, position={lineno}:{column})'.format(
            type=self.type,
            value=repr(self.value),
            lineno=self.lineno,
            column=self.column,
        )

    def __repr__(self):
        return self.__str__()


RESERVED_KEYWORDS = {
    'BEGIN': Token(BEGIN, 'BEGIN'),
    'END': Token(END, 'END'),
    'PROGRAM': Token(PROGRAM, 'PROGRAM'),
    'VAR': Token(VAR, 'VAR'),
    'DIV': Token(INTEGER_DIV, 'DIV'),
    'INTEGER': Token(INTEGER, 'INTEGER'),
    'REAL': Token(REAL, 'REAL'),
    'PROCEDURE': Token(PROCEDURE, 'PROCEDURE'),
}
