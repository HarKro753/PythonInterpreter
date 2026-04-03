BEGIN
    x := 2;
    y := 3;
    z := x + y * 2;
    BEGIN
        a := z + 1
    END;
    b := a - x
END.
