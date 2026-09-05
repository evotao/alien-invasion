import java.nio.file.*;
import jdk.internal.org.objectweb.asm.*;

// The old obfuscator emitted invalid LocalVariableTable names. Remove debug
// attributes so a current JVM can load the original instructions.
public class StripDebug {
    public static void main(String[] args) throws Exception {
        Path output=Path.of("reference/classes/clean"); Files.createDirectories(output);
        try (var files=Files.list(Path.of("reference/AlienInvasion"))) {
            for(Path path:files.filter(p->p.toString().endsWith(".class")).toList()) {
                ClassReader reader=new ClassReader(Files.readAllBytes(path));
                ClassWriter writer=new ClassWriter(0);
                reader.accept(writer,ClassReader.SKIP_DEBUG);
                Files.write(output.resolve(path.getFileName()),writer.toByteArray());
            }
        }
    }
}
