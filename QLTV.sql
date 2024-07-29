
CREATE TABLE Books (
    Book_Id INT AUTO_INCREMENT PRIMARY KEY,
    Entry_Date DATE NOT NULL,
    Price FLOAT NOT NULL,
    Quantity INT NOT NULL,
    Publisher VARCHAR(100),
    `Condition` VARCHAR(20) NULL,  -- For Textbooks
    Tax FLOAT NULL  -- For ReferenceBooks
);