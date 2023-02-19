plugins {
	java
	groovy
	id("org.springframework.boot") version "2.7.8"
	id("io.spring.dependency-management") version "1.1.0"
}

group = "com.devgs"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

extra["testcontainersVersion"] = "1.17.6"

dependencies {
	implementation("org.liquibase:liquibase-core")
	runtimeOnly("org.postgresql:postgresql")
	implementation(platform("org.apache.groovy:groovy-bom:4.0.5"))
	implementation("org.apache.groovy:groovy")
	implementation("org.liquibase:liquibase-core:4.19.0")
	implementation("org.springframework.boot:spring-boot-starter-data-jdbc")


	compileOnly("org.projectlombok:lombok:1.18.26")
	annotationProcessor("org.projectlombok:lombok:1.18.26")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.testcontainers:spock")
	testImplementation("org.testcontainers:postgresql")
	testImplementation(platform("org.spockframework:spock-bom:2.3-groovy-4.0"))
	testImplementation("org.spockframework:spock-core")
	testImplementation("org.spockframework:spock-spring")
	testImplementation("org.hamcrest:hamcrest-core:2.2")
	testRuntimeOnly("net.bytebuddy:byte-buddy:1.12.17")
	testRuntimeOnly("org.objenesis:objenesis:3.3")
	testCompileOnly("org.projectlombok:lombok:1.18.26")
	testAnnotationProcessor("org.projectlombok:lombok:1.18.26")

}

dependencyManagement {
	imports {
		mavenBom("org.testcontainers:testcontainers-bom:${property("testcontainersVersion")}")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
