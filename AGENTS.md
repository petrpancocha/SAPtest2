# AGENTS.md

## Project overview

- This is a small Java 21 command-line application in the package `com.petrpancocha.saptest`.
- The application models zones, roads and complaints, builds a bidirectional graph, enumerates simple paths from the headquarters, and prints travel and rest-time calculations.
- The code is currently a single package without separate application, domain, or infrastructure modules. Keep changes consistent with this simple structure unless the task calls for an architectural change.
- `Main` owns the sample input data and coordinates object creation, graph construction, path conversion, and output. `Graph` owns adjacency-list storage and depth-first enumeration of paths. `Zone`, `Road`, `Complaint`, and `Path` are the model types.
- Zone IDs in the model are 1-based; graph mapping IDs are 0-based (`id - 1`). `Graph.addEdge` adds edges in both directions.

## Repository layout and tools

- `src/main/java/com/petrpancocha/saptest/`: application source.
- `src/test/java/com/petrpancocha/saptest/`: JUnit 5 tests.
- `pom.xml`: Maven project configuration, Java release 21, JUnit Jupiter, Surefire, and JaCoCo.
- Maven's test source root is `src`, so `testCompile` compiles the main and test Java files together. Keep the tests physically under `src/test/java`; do not reset the Maven test source root without verifying a clean test build.
- `.idea/` and `SAPtest2.iml`: IntelliJ IDEA project metadata; `.idea` and `*.iml` are ignored by Git.
- `out/` is the IntelliJ/compiler output directory and `target/` is Maven output; both are ignored by Git.
- No CI workflow, container definition, deployment manifest, or deployment procedure is present in the repository. Do not invent or claim one.

## Build, run, and test

Use JDK 21. Maven is the configured build and test tool:

```powershell
mvn test
```

Run the configured JaCoCo coverage check and generate the report with:

```powershell
mvn verify
```

The JaCoCo configuration enforces at least 90% bundle line coverage at `verify`. Its HTML report is generated at `target/site/jacoco/index.html` when the build reaches the report goal.

Run the application after compiling with Maven:

```powershell
mvn package
java -cp target/classes com.petrpancocha.saptest.Main
```

There is no configured executable/fat JAR or Maven run plugin. The project can also be compiled directly with the installed JDK, but that does not run the JUnit suite or coverage check:

```powershell
$files = Get-ChildItem src/main/java -Recurse -Filter *.java | ForEach-Object FullName
javac -Xlint:unchecked -d out $files
java -cp out com.petrpancocha.saptest.Main
```

Never report a build, test, or coverage check as successful unless its command completed successfully. The test source root is configured as `src` because compiling main and test sources together avoids a test-compiler classpath failure in this project. Do not lower or bypass the JaCoCo threshold to make the build pass.

## Development workflow

1. Read the relevant source and tests before changing behavior. Keep the domain model and graph mapping conventions in mind.
2. Make the smallest change that addresses the task. Avoid adding frameworks, modules, plugins, or runtime dependencies without a concrete need.
3. Add or update JUnit 5 tests under `src/test/java` for behavior changes. Prefer assertions about returned paths and calculated values; the existing `MainTest` captures console output because `Main` currently prints directly.
4. Run `mvn test` for test changes and `mvn verify` when checking the configured coverage threshold. If Maven cannot compile tests, report the concrete failure and do not claim test or coverage success.
5. Keep generated output under ignored `out/` or `target/`. Do not commit compiled classes, reports, logs, IDE workspace files, or local credentials.
6. Update documentation when commands, project structure, or behavior change. Keep claims aligned with checked-in configuration and observed behavior.

## Implementation guidance

- Preserve Java 21 compatibility as declared by `pom.xml` and `.idea/misc.xml`.
- Keep graph vertices 0-based internally and convert model IDs through `Zone.getGraphMappingId()`; avoid mixing model IDs and graph indices.
- `Graph.findAllPaths` enumerates all simple paths, rather than computing only a shortest path. Consider graph size and path growth before extending its use to larger inputs.
- The sample roads and complaints are currently hard-coded in `Main`. Do not describe the program as reading external configuration or user input unless that functionality is implemented.
- There is no logging framework or application configuration layer. Preserve the current straightforward console behavior unless requirements call for a change.
- Avoid introducing unchecked generic operations. Use parameterized collections such as `List<List<Integer>>` for graph adjacency.

## Security and Git

- Do not add secrets, personal access tokens, private keys, or machine-specific paths to source, tests, documentation, or build configuration.
- Treat graph indices and model data as inputs that may need validation when exposing them beyond the current fixed sample. Do not silently invent new validation semantics; define them in tests and documentation when requirements establish them.
- Do not add deployment, network, or external-service behavior without an explicit requirement and a concrete implementation plan.
- The repository has a Git remote named `origin` and a `master` branch. Review `git status` before editing or committing, preserve unrelated user changes, and push only when the user explicitly requests it.
