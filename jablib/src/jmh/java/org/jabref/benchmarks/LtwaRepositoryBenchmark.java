package org.jabref.benchmarks;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jabref.logic.journals.JournalAbbreviationRepository;
import org.jabref.logic.journals.ltwa.LtwaEntry;
import org.jabref.logic.journals.ltwa.PrefixTree;

import org.h2.mvstore.MVMap;
import org.h2.mvstore.MVStore;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Warmup(iterations = 0)
@Measurement(iterations = 1, time = 200, timeUnit = TimeUnit.MILLISECONDS)
@Fork(1)
public class LtwaRepositoryBenchmark {
    private Path ltwaListFile;

    @Setup(Level.Invocation) // run this setup before every invocation of the benchmark
    public void prepare() throws IOException {
        try (InputStream resourceAsStream = JournalAbbreviationRepository.class.getResourceAsStream("/journals/ltwa-list.mv")) {
            if (resourceAsStream == null) {
                throw new IOException("LTWA repository not found");
            }
            Path tempDir = Files.createTempDirectory("jabref-ltwa");
            ltwaListFile = tempDir.resolve("ltwa-list.mv");
            Files.copy(resourceAsStream, ltwaListFile);
        }
    }

    @Benchmark
    public void createLtwaRepository() throws IOException {
        PrefixTree<LtwaEntry> prefix = new PrefixTree<>();
        PrefixTree<LtwaEntry> suffix = new PrefixTree<>();

        final String PREFIX_MAP_NAME = "Prefixes";
        final String SUFFIX_MAP_NAME = "Suffixes";

        try (MVStore store = new MVStore.Builder().readOnly().fileName(ltwaListFile.toAbsolutePath().toString()).open()) {
            MVMap<String, List<LtwaEntry>> prefixMap = store.openMap(PREFIX_MAP_NAME);
            MVMap<String, List<LtwaEntry>> suffixMap = store.openMap(SUFFIX_MAP_NAME);

            for (String key : prefixMap.keySet()) {
                List<LtwaEntry> value = prefixMap.get(key);
                if (value != null) {
                    prefix.insert(key, value);
                }
            }

            for (String key : suffixMap.keySet()) {
                List<LtwaEntry> value = suffixMap.get(key);
                if (value != null) {
                    suffix.insert(key, value);
                }
            }
        }
    }
}
