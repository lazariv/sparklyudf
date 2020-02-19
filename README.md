
# How to build and import R extension package 

### Check prerequisities 

#### Check Scala compiler

```{r}
library(sparklyr)
find_scalac(version="2.11")
download_scalac(dest_path="/opt/scala")
```

#### Check Apache Spark

```{r}
Sys.setenv(SPARK_HOME="/opt/spark-2.2.0-bin-hadoop2.7")
Sys.setenv(JAVA_HOME="/usr/lib/jvm/java-8-openjdk-amd64")
```

### Build

#### Build the jars
```{r}
spec <- sparklyr::spark_default_compilation_spec()
spec <- Filter(function(e) e$spark_version >= "2.2.0", spec)
sparklyr::compile_package_jars(spec=spec)
```
#### Build a package (from `$HOME/R/mypackages`)

```{bash}
R CMD build sparklyudf
```

#### Install package from source
```{r}
install.packages("$HOME/R/mypackages/sparklyudf_0.1.0.tar.gz",repos=NULL,type="source")
```

#### How to use:

```{r}
library(sparklyudf)
library(sparklyr)
library(dplyr)

sc <- spark_connect(master = "spark-master")
sparklyudf_register(sc)
data.frame(path = "some_data") %>%
  copy_to(sc, .) %>%
  mutate(file_name = get_only_file_name(path))
```



### More Info

Info on:  https://github.com/javierluraschi/sparklyudf
and on https://spark.rstudio.com/reference/#section-extensions