create
    definer = root@localhost procedure kodilla_course.UpdateBestseller()
BEGIN
    declare bookRentCount, days, bookId INT;
    declare booksPerMonth DECIMAL(5, 2);
    declare FINISHED INT DEFAULT 0;
    declare allBooks CURSOR FOR SELECT BOOK_ID FROM books;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET FINISHED = 1;
    open allBooks;
    WHILE (FINISHED = 0)
        DO
            FETCH allBooks into bookId;
            IF (FINISHED = 0) THEN
                SELECT COUNT(*)
                FROM rents
                where BOOK_ID = bookId
                into bookRentCount;

                SELECT DATEDIFF(MAX(RENT_DATE), MIN(RENT_DATE)) + 1
                FROM RENTS
                where BOOK_ID = bookId
                into days;

                set booksPerMonth = bookRentCount / days * 30;

                if booksPerMonth > 2 then
                    UPDATE books
                    set books.BESTSELLER = TRUE
                    WHERE BOOK_ID = bookId;
                else
                    UPDATE books
                    set books.BESTSELLER = FALSE
                    WHERE BOOK_ID = bookId;
                end if;
            end if;
        end while;
    close allBooks;
End;


