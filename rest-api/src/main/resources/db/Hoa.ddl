CREATE TABLE DataSupplier
(
    cntyCd VARCHAR(5) PRIMARY KEY NOT NULL
);
CREATE TABLE Hoa
(
    hoaId INT PRIMARY KEY NOT NULL,
    cntyCd VARCHAR(5) NOT NULL,
    stdHoaNm VARCHAR(100) NOT NULL
);
CREATE TABLE HoaContactLink
(
    hoaContactId INT NOT NULL,
    hoaId INT NOT NULL,
    PRIMARY KEY ( hoaContactId, hoaId )
);
CREATE TABLE HoaProperty
(
    hoaId INT NOT NULL,
    cntyCd VARCHAR(5) NOT NULL,
    pclId VARCHAR(45) NOT NULL,
    pclSeqNbr INT NOT NULL,
    situsStdAddr VARCHAR(60),
    situsStdCityName VARCHAR(40),
    situsStdStCd VARCHAR(2),
    situsStdZipCd VARCHAR(9),
    PRIMARY KEY ( hoaId, cntyCd, pclId, pclSeqNbr )
);
CREATE TABLE HoaContact
(
    hoaContactId INT PRIMARY KEY NOT NULL,
    cntyCd VARCHAR(5) NOT NULL,
    stdContactNm VARCHAR(60) NOT NULL,
    lastRecordingDt TIMESTAMP,
    stdAddr1 VARCHAR(60),
    stdAddr2 VARCHAR(60),
    stdAddr3 VARCHAR(60),
    stdAddr4 VARCHAR(60),
    stdCityName VARCHAR(40),
    stdStCd VARCHAR(2),
    stdZipCd VARCHAR(9)
);
ALTER TABLE Hoa ADD FOREIGN KEY ( cntyCd ) REFERENCES DataSupplier ( cntyCd );
CREATE UNIQUE INDEX cntyCd_stdHoaNm_UNIQUE ON Hoa ( cntyCd, stdHoaNm );
ALTER TABLE HoaContactLink ADD FOREIGN KEY ( hoaContactId ) REFERENCES HoaContact ( hoaContactId );
ALTER TABLE HoaContactLink ADD FOREIGN KEY ( hoaId ) REFERENCES Hoa ( hoaId );
ALTER TABLE HoaProperty ADD FOREIGN KEY ( hoaId ) REFERENCES Hoa ( hoaId );
ALTER TABLE HoaContact ADD FOREIGN KEY ( cntyCd ) REFERENCES DataSupplier ( cntyCd );
CREATE UNIQUE INDEX cntyCd_stdContactNm_UNIQUE ON HoaContact ( cntyCd, stdContactNm );

