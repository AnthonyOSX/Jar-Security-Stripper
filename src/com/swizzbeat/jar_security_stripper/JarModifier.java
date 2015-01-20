package com.swizzbeat.jar_security_stripper;

import com.swizzbeat.jar_security_stripper.asm.AbstractTransformer;
import com.swizzbeat.jar_security_stripper.asm.impl.FieldAccessTransformer;
import com.swizzbeat.jar_security_stripper.asm.impl.MethodAccessTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.io.*;
import java.util.*;
import java.util.jar.*;

/**
 * Created with IntelliJ IDEA
 * User: Anthony
 * Date: 12/19/14
 */

public class JarModifier {

    private static final Map<String, ClassNode> classes;
    private static final List<AbstractTransformer> transformers;

    private static Manifest manifest;

    static {
        classes = new HashMap<>();
        transformers = new ArrayList<>();
    }

    private JarModifier() {
        // Singleton
    }

    public static void run(File file) throws IOException {
        System.out.println("Loading jar into memory...");

        loadJar(new JarFile(file));

        System.out.println("Transforming classes...");

        loadTransformers();
        runTransformers();

        System.out.println("Writing modified jar file...");

        dumpJar(file);

        System.out.println("Modifications complete.");
    }

    private static void loadJar(JarFile jar) throws IOException {
        Enumeration<JarEntry> enumeration = jar.entries();

        while (enumeration.hasMoreElements()) {
            JarEntry entry = enumeration.nextElement();

            if (entry.getName().endsWith(".class")) {
                try (InputStream is = jar.getInputStream(entry)) {
                    ClassReader reader = new ClassReader(is);
                    ClassNode node = new ClassNode();

                    reader.accept(node, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
                    classes.put(node.name, node);
                }
            }
        }

        manifest = jar.getManifest();
    }

    private static void dumpJar(File file) throws IOException {
        try (JarOutputStream jos = new JarOutputStream(new FileOutputStream(file), manifest)) {
            for (ClassNode cn : classes.values()) {
                ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);

                jos.putNextEntry(new JarEntry(cn.name + ".class"));
                cn.accept(writer);
                jos.write(writer.toByteArray());
            }
        }
    }

    private static void loadTransformers() {
        transformers.add(new FieldAccessTransformer());
        transformers.add(new MethodAccessTransformer());
    }

    private static void runTransformers() {
        for (ClassNode cn : classes.values()) {
            for (AbstractTransformer t : transformers) {
                t.run(cn);
            }
        }
    }

}
