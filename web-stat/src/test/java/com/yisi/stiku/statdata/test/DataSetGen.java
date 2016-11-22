package com.yisi.stiku.statdata.test;

import java.io.FileOutputStream;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.junit.Test;
import org.unitils.UnitilsJUnit4;
import org.unitils.database.annotations.TestDataSource;

public class DataSetGen extends UnitilsJUnit4 {

	@TestDataSource("database1")
	private DataSource dataSource;

	private static IDatabaseConnection conn;

	@Test
	public void gen()
	{

		QueryDataSet dataSet;
		IDatabaseConnection databaseConnection;

		try {
			databaseConnection = new DatabaseConnection(dataSource.getConnection());
			dataSet = new QueryDataSet(databaseConnection);
			String tableName = "class_fenban_data";
			dataSet.addTable(tableName, "select * from " + tableName);
			FlatXmlDataSet.write(dataSet, new FileOutputStream(ReportServiceTest.class.getSimpleName() + tableName
					+ "_dataset.xml"));
		} catch (Exception e) {

			e.printStackTrace();
		}

	}
}
