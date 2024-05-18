package com.baidu.spi.asm;

import com.github.javaparser.utils.Log;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.util.Map;
import java.util.function.BiConsumer;

public class ServiceProviderDump implements Opcodes {

    public static byte[] dump (Map<String, String> callMap) throws Exception {

        ClassWriter classWriter = new ClassWriter(0);
        FieldVisitor fieldVisitor;
        MethodVisitor methodVisitor;

        classWriter.visit(V1_8, ACC_PUBLIC | ACC_SUPER, "com/baidu/spi/ServiceProvider", null, "java/lang/Object", null);

        classWriter.visitInnerClass("java/util/Map$Entry", "java/util/Map", "Entry", ACC_PUBLIC | ACC_STATIC | ACC_ABSTRACT | ACC_INTERFACE);

        {
            fieldVisitor = classWriter.visitField(ACC_PRIVATE | ACC_FINAL | ACC_STATIC, "sProviders", "Ljava/util/Map;", "Ljava/util/Map<Ljava/lang/Class<*>;Ljava/util/concurrent/Callable<*>;>;", null);
            fieldVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_PRIVATE, "<init>", "()V", null, null);
            methodVisitor.visitCode();
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            methodVisitor.visitInsn(RETURN);
            methodVisitor.visitMaxs(1, 1);
            methodVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_STATIC | ACC_SYNCHRONIZED, "register", "(Ljava/lang/Class;Ljava/util/concurrent/Callable;)V", "(Ljava/lang/Class<*>;Ljava/util/concurrent/Callable<*>;)V", null);
            methodVisitor.visitCode();
            methodVisitor.visitFieldInsn(GETSTATIC, "com/baidu/spi/ServiceProvider", "sProviders", "Ljava/util/Map;");
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitVarInsn(ALOAD, 1);
            methodVisitor.visitMethodInsn(INVOKEINTERFACE, "java/util/Map", "put", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", true);
            methodVisitor.visitInsn(POP);
            methodVisitor.visitInsn(RETURN);
            methodVisitor.visitMaxs(3, 2);
            methodVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_STATIC | ACC_SYNCHRONIZED, "newProvider", "(Ljava/lang/Class;)Ljava/lang/Object;", "<S:Ljava/lang/Object;>(Ljava/lang/Class<TS;>;)TS;", new String[] { "java/lang/Exception" });
            methodVisitor.visitCode();
            methodVisitor.visitFieldInsn(GETSTATIC, "com/baidu/spi/ServiceProvider", "sProviders", "Ljava/util/Map;");
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitMethodInsn(INVOKEINTERFACE, "java/util/Map", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", true);
            methodVisitor.visitTypeInsn(CHECKCAST, "java/util/concurrent/Callable");
            methodVisitor.visitMethodInsn(INVOKEINTERFACE, "java/util/concurrent/Callable", "call", "()Ljava/lang/Object;", true);
            methodVisitor.visitInsn(ARETURN);
            methodVisitor.visitMaxs(2, 1);
            methodVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_STATIC, "<clinit>", "()V", null, null);
            methodVisitor.visitCode();
            methodVisitor.visitTypeInsn(NEW, "java/util/LinkedHashMap");
            methodVisitor.visitInsn(DUP);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/util/LinkedHashMap", "<init>", "()V", false);
            methodVisitor.visitFieldInsn(PUTSTATIC, "com/baidu/spi/ServiceProvider", "sProviders", "Ljava/util/Map;");

            //注册单个Callable
            /*methodVisitor.visitLdcInsn(Type.getType("Lcom/baidu/spi/Apple;"));
            methodVisitor.visitTypeInsn(NEW, "com/baidu/spi/AppleCallable");
            methodVisitor.visitInsn(DUP);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "com/baidu/spi/AppleCallable", "<init>", "()V", false);
            methodVisitor.visitMethodInsn(INVOKESTATIC, "com/baidu/spi/ServiceProvider", "register", "(Ljava/lang/Class;Ljava/util/concurrent/Callable;)V", false);
            */
            //注册多个Callable
            for (Map.Entry<String, String> entry : callMap.entrySet()) {
                System.out.println("entry.getKey()  " + entry.getKey() + "  entry.getValue() " + entry.getValue());
                String key = entry.getKey().replace(".", "/");
                String value = entry.getValue().replace(".", "/");
                methodVisitor.visitLdcInsn(Type.getType("L"+key+";"));
                methodVisitor.visitTypeInsn(NEW, value);
                methodVisitor.visitInsn(DUP);
                methodVisitor.visitMethodInsn(INVOKESPECIAL, value, "<init>", "()V", false);
                methodVisitor.visitMethodInsn(INVOKESTATIC, "com/baidu/spi/ServiceProvider", "register", "(Ljava/lang/Class;Ljava/util/concurrent/Callable;)V", false);
            }

            methodVisitor.visitInsn(RETURN);
            methodVisitor.visitMaxs(3, 0);
            methodVisitor.visitEnd();
        }
        classWriter.visitEnd();

        return classWriter.toByteArray();
    }
}
