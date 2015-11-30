drop table if exists Swaps;
drop table if exists Report;
drop table if exists Orders;
drop table if exists Trader;
drop table if exists Commodity;
 
CREATE TABLE Trader (
    name CHAR(30) NOT NULL, traderId INT(10) NOT NULL,
    PRIMARY KEY(traderId)
)   ENGINE=INNODB;

CREATE TABLE Swaps (
    swapId Int(10) Not Null Auto_Increment,
    start CHAR(10) NOT NULL,
    termination CHAR(10) NOT NULL,
    floatRate CHAR(10) NOT NULL,
    spread FLOAT Not Null,
    fixedRate FLOAT NOT NULL,
    fixedPayer CHAR(10) NOT NULL, 
    trader INT(10) NOT NULL,
    parValue FLOAT NOT NULL,
    date Date NOT NULL, 
    time TIME NOT NULL,
    PRIMARY KEY(swapId),
    INDEX (trader), 
    FOREIGN KEY (trader)
      REFERENCES Trader(traderId)
)   ENGINE=INNODB; 
CREATE TABLE Commodity (
    symbol CHAR(10) NOT NULL,
    expire_date Date, 
    PRIMARY KEY (symbol,expire_date)
)   ENGINE=INNODB;
 
CREATE TABLE Orders (
    orderId Int(10) Not Null Auto_Increment,
    orderType CHAR(10) NOT NULL,
    traderId Int(10) NOT NULL,
    symbol CHAR(10) NOT NULL,
    expire_date Date Not Null,
    action CHAR(10) NOT NULL,
    price FLOAT, lots INT(10),
    date Date, 
    time TIME,
    PRIMARY KEY(orderId),
    INDEX (symbol, expire_date),
    INDEX (traderId),
 
    FOREIGN KEY (symbol, expire_date)
      REFERENCES Commodity(symbol, expire_date), 
    FOREIGN KEY (traderId)
      REFERENCES Trader(traderId)
)   ENGINE=INNODB;
 
CREATE TABLE Report (
    reportId Int(10) NOT NULL,
    orderId Int(10) NOT NULL,
    price FLOAT NOT NULL,
    lots INT(10) NOT NULL,
    transactTime CHAR(30) NOT NULL, 
    PRIMARY KEY(reportId),
    INDEX (orderId),
 
    FOREIGN KEY (orderId)
      REFERENCES Orders(orderId)
)   ENGINE=INNODB;









