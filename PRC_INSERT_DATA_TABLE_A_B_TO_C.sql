CREATE OR REPLACE PROCEDURE PRC_INSERT_DATA_TABLE_A_B_TO_C AS
DECLARE
  V_STATUS_ISS NUMBER;
  V_STATUS_ACQ NUMBER;
  V_STATUS_DEST NUMBER;
BEGIN
  
  FOR REC IN(
	SELECT 
		A.ID AS ID,
		A.CARDNUMBER AS CARDNUMBER,
		A.ISS AS ISS,
		A.ACQ AS ACQ,
		A.DEST AS DEST,
		A.STATUS AS STATUS_A,
		B.STATUS AS STATUS_B,
		B.SOURCE AS SOURCE_B
	FROM TABLE_A A, TABLE_B B
	where A.ID = B.ID
  )LOOP
	BEGIN
		IF REC.STATUS_B = '0' AND REC.SOURCE_B = 'MDR' THEN
		 IF REC.ISS = 'MDR' THEN
		   V_STATUS_ISS := 0;	
		 ELSE
		   V_STATUS_ISS := 1;
		 END IF;
		 IF REC.ACQ = 'MDR' THEN
		   V_STATUS_ACQ := 0;	
		 ELSE
		   V_STATUS_ACQ := 1;
		 END IF;
		 IF REC.DEST = 'MDR' THEN
		   V_STATUS_DEST := 0;	
		 ELSE
		   V_STATUS_DEST := 1;
		 END IF;
		ELSIF REC.STATUS_B = '1' AND REC SOURCE_B = 'BNI' THEN
		 IF REC.ISS = 'BNI' THEN
		   V_STATUS_ISS := 1;	
		 ELSE
		   V_STATUS_ISS := 0;
		 END IF;
		 IF REC.ACQ = 'BNI' THEN
		   V_STATUS_ACQ := 1;	
		 ELSE
		   V_STATUS_ACQ := 0;
		 END IF;
		 IF REC.DEST = 'BNI' THEN
		   V_STATUS_DEST := 1;	
		 ELSE
		   V_STATUS_DEST := 0;
		 END IF;
		END IF;
	EXCEPTION OTHERS THEN
		DBMS_OUTPUT.PUTLINE('GAGAL ' || SQLERRM);
	END;
  	BEGIN
		INSERT INTO TABLE_C
			(ID,
			 CARDNUMBER,
			 ISS,
			 ACQ,
			 DEST,
			 STATUS_A,
			 STATUS_ISS,
			 STATUS_ACQ,
			 STATUS_DEST)
		 VALUES
			(REC.ID,
			REC.CARDNUMBER,
			REC.ISS,
			REC.ACQ,
			REC.DEST,
			REC.STATUS_A,
			V_STATUS_ISS, 
			V_STATUS_ACQ, 
			V_STATUS_DEST)
	EXCEPTION OTHERS THEN
		DBMS_OUTPUT.PUTLINE('GAGAL ' || SQLERRM);
	END;
  ENDLOOP;  
EXCEPTION OTHERS THEN
	DBMS_OUTPUT.PUTLINE('GAGAL ' || SQLERRM);
END;









































CREATE OR REPLACE PROCEDURE insert_data_from_a_and_b_to_c AS
BEGIN
  -- Insert data from Table A into Table C
  INSERT INTO table_c (id, cardnumber, ISS, ACQ, DEST, STATUS_A, STATUS_ISS, STATUS_ACQ, STATUS_DEST)
  SELECT id, cardnumber, ISS, ACQ, DEST, STATUS, 0, 0, 0 FROM table_a;

  -- Insert data from Table B into Table C
  FOR b_rec IN (SELECT * FROM table_b) LOOP
    UPDATE table_c
    SET STATUS_ISS = b_rec.STATUS
    WHERE cardnumber = b_rec.cardnumber AND ISS = b_rec.ISS;

    UPDATE table_c
    SET STATUS_ACQ = b_rec.STATUS
    WHERE cardnumber = b_rec.cardnumber AND ACQ = b_rec.ACQ;

    UPDATE table_c
    SET STATUS_DEST = b_rec.STATUS
    WHERE cardnumber = b_rec.cardnumber AND DEST = b_rec.DEST;
  END LOOP;
  
  COMMIT;
END;

