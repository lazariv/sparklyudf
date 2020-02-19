package sparklyudf

import org.apache.spark.sql.SparkSession;

object Main {
  def register_fun(spark: SparkSession) = {
	  spark.udf.register("get_only_file_name", (fullPath: String) => fullPath.split("/").last)
  }
}
