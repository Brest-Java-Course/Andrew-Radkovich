SELECT c.customer_id, t.data, t.location, t.title, t.cost FROM CUSTOMER c JOIN TICKET t ON c.customer_id = t.customer_id WHERE data=:data;