/* ##################################################################

                  auth-service tables (MySQL)

                       by Dylan Legendre
                 http://www.snowlynxsoftware.net
                Copyright 2018. All Rights Reserved.

###################################################################### */

CREATE TABLE Users
(
  ID INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  CreateDate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  LastEditDate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  InActive BIT(1) NOT NULL DEFAULT 0,
  Verified BIT(1) NOT NULL DEFAULT 0,
  Email VARCHAR(255) NOT NULL,
  Hash TEXT NOT NULL,
  AuthCode INT(6) NOT NULL,
  LastLogin DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  LoginAttempts INT NOT NULL DEFAULT 0
);

CREATE TABLE AuthTokens
(
  ID BINARY(16) NOT NULL PRIMARY KEY,
  RefreshToken VARCHAR(255) NOT NULL,
  AuthToken VARCHAR(255) NOT NULL,
  Secret VARCHAR(255) NOT NULL
);
