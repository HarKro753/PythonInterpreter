PROGRAM Part18;
VAR
    x, y : INTEGER;

PROCEDURE Alpha(a : INTEGER; b : INTEGER);
VAR
    z : INTEGER;
BEGIN
    z := a + b + x
END;

PROCEDURE Beta(p : INTEGER);
VAR
    local : INTEGER;
BEGIN
    local := p * 2;
    x := local
END;

BEGIN {Part18}
    x := 10;
    Alpha(3, 7);
    Beta(5);
    y := x + 1
END.
