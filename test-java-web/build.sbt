name := """test-java-web"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"


libraryDependencies ++= Seq(
  "com.impetus.kundera.core" % "kundera-core" % "3.8",
  "com.impetus.kundera.client" % "kundera-cassandra" % "3.8",
  "com.impetus.kundera.core" % "fallback-impl" % "3.8",
  "org.apache.cassandra" % "cassandra-thrift" % "3.10",
  "com.datastax.cassandra" % "cassandra-driver-core" % "3.1.4",
  "org.apache.cassandra" % "cassandra-all" % "3.10",
  "com.datastax.cassandra" % "cassandra-driver-mapping" % "3.1.4",
  "org.apache-extras.cassandra-jdbc" % "cassandra-jdbc" % "1.2.5"
)

libraryDependencies += filters

        
        

resolvers ++= Seq(
  Resolver.url("Kundera", new URL("https://oss.sonatype.org/content/repositories/releases/"))(Resolver.ivyStylePatterns),
  Resolver.url("Riptano", new URL("http://mvn.riptano.com/content/repositories/public/"))(Resolver.ivyStylePatterns),
  Resolver.url("Kundera missing", new URL("http://kundera.googlecode.com/svn/maven2/maven-missing-resources/"))(Resolver.ivyStylePatterns),
  Resolver.url("Scale 7", new URL("https://github.com/s7/mvnrepo/raw/master/"))(Resolver.ivyStylePatterns)
)



// Compile the project before generating Eclipse files, so that generated .scala or .class files for views and routes are present
EclipseKeys.preTasks := Seq(compile in Compile)
EclipseKeys.projectFlavor := EclipseProjectFlavor.Java           // Java project. Don't expect Scala IDE
EclipseKeys.createSrc := EclipseCreateSrc.ValueSet(EclipseCreateSrc.ManagedClasses, EclipseCreateSrc.ManagedResources)  // Use .class files instead of generated .scala files for views and routes

fork in run := true