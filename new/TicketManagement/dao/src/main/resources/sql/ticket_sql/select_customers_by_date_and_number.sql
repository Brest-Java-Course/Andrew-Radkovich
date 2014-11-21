SELECT c.customer_id, c.name, c.identificationNumber
FROM CUSTOMER c JOIN TICKET t ON c.customer_id = t.customer_id
WHERE data=:data AND identificationNumber=:identificationNumber;