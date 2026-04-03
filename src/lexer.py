from tokens import (
    Token, INTEGER_CONST, REAL_CONST, PLUS, MINUS, MUL, FLOAT_DIV,
    LPAREN, RPAREN, EOF, DOT, ASSIGN, SEMI, ID, COLON, COMMA,
    RESERVED_KEYWORDS
)
from errors import LexerError


class Lexer(object):
    def __init__(self, text):
        self.text = text
        self.pos = 0
        self.current_char = self.text[self.pos]
        self.lineno = 1
        self.column = 1

    def error(self):
        raise LexerError(
            message="Lexer error on '{}' line: {} column: {}".format(
                self.current_char, self.lineno, self.column
            )
        )

    def advance(self):
        if self.current_char == '\n':
            self.lineno += 1
            self.column = 0

        self.pos += 1
        if self.pos > len(self.text) - 1:
            self.current_char = None
        else:
            self.current_char = self.text[self.pos]
            self.column += 1

    def peek(self):
        peek_pos = self.pos + 1
        if peek_pos > len(self.text) - 1:
            return None
        return self.text[peek_pos]

    def skip_whitespace(self):
        while self.current_char is not None and self.current_char.isspace():
            self.advance()

    def skip_comment(self):
        while self.current_char != '}':
            self.advance()
        self.advance()

    def number(self):
        token = Token(type=None, value=None, lineno=self.lineno, column=self.column)
        result = ''
        while self.current_char is not None and self.current_char.isdigit():
            result += self.current_char
            self.advance()

        if self.current_char == '.':
            result += self.current_char
            self.advance()
            while self.current_char is not None and self.current_char.isdigit():
                result += self.current_char
                self.advance()
            token.type = REAL_CONST
            token.value = float(result)
        else:
            token.type = INTEGER_CONST
            token.value = int(result)

        return token

    def _id(self):
        token = Token(type=None, value=None, lineno=self.lineno, column=self.column)
        result = ''
        while self.current_char is not None and (self.current_char.isalnum() or self.current_char == '_'):
            result += self.current_char
            self.advance()

        reserved = RESERVED_KEYWORDS.get(result.upper())
        if reserved is not None:
            token.type = reserved.type
            token.value = reserved.value
        else:
            token.type = ID
            token.value = result

        return token

    def get_next_token(self):
        while self.current_char is not None:

            if self.current_char.isspace():
                self.skip_whitespace()
                continue

            if self.current_char == '{':
                self.advance()
                self.skip_comment()
                continue

            if self.current_char.isalpha() or self.current_char == '_':
                return self._id()

            if self.current_char.isdigit():
                return self.number()

            if self.current_char == ':' and self.peek() == '=':
                token = Token(ASSIGN, ':=', self.lineno, self.column)
                self.advance()
                self.advance()
                return token

            if self.current_char == ':':
                token = Token(COLON, ':', self.lineno, self.column)
                self.advance()
                return token

            if self.current_char == ',':
                token = Token(COMMA, ',', self.lineno, self.column)
                self.advance()
                return token

            if self.current_char == ';':
                token = Token(SEMI, ';', self.lineno, self.column)
                self.advance()
                return token

            if self.current_char == '.':
                token = Token(DOT, '.', self.lineno, self.column)
                self.advance()
                return token

            if self.current_char == '+':
                token = Token(PLUS, '+', self.lineno, self.column)
                self.advance()
                return token

            if self.current_char == '-':
                token = Token(MINUS, '-', self.lineno, self.column)
                self.advance()
                return token

            if self.current_char == '*':
                token = Token(MUL, '*', self.lineno, self.column)
                self.advance()
                return token

            if self.current_char == '/':
                token = Token(FLOAT_DIV, '/', self.lineno, self.column)
                self.advance()
                return token

            if self.current_char == '(':
                token = Token(LPAREN, '(', self.lineno, self.column)
                self.advance()
                return token

            if self.current_char == ')':
                token = Token(RPAREN, ')', self.lineno, self.column)
                self.advance()
                return token

            self.error()

        return Token(EOF, None, self.lineno, self.column)
