package com.baidu.spi;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class ServiceProviderDump implements Opcodes {

    public static byte[] dump () throws Exception {

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
            methodVisitor.visitMethodInsn(INVOKEINTERFACE, "java/util/Map", "entrySet", "()Ljava/util/Set;", true);
            methodVisitor.visitMethodInsn(INVOKEINTERFACE, "java/util/Set", "iterator", "()Ljava/util/Iterator;", true);
            methodVisitor.visitVarInsn(ASTORE, 1);
            Label label0 = new Label();
            methodVisitor.visitLabel(label0);
            methodVisitor.visitVarInsn(ALOAD, 1);
            methodVisitor.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "hasNext", "()Z", true);
            Label label1 = new Label();
            methodVisitor.visitJumpInsn(IFEQ, label1);
            methodVisitor.visitVarInsn(ALOAD, 1);
            methodVisitor.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "next", "()Ljava/lang/Object;", true);
            methodVisitor.visitTypeInsn(CHECKCAST, "java/util/Map$Entry");
            methodVisitor.visitVarInsn(ASTORE, 2);
            methodVisitor.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            methodVisitor.visitTypeInsn(NEW, "java/lang/StringBuilder");
            methodVisitor.visitInsn(DUP);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
            methodVisitor.visitLdcInsn("ServiceProvider newProvider provider entry.getKey()  ");
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            methodVisitor.visitVarInsn(ALOAD, 2);
            methodVisitor.visitMethodInsn(INVOKEINTERFACE, "java/util/Map$Entry", "getKey", "()Ljava/lang/Object;", true);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/Object;)Ljava/lang/StringBuilder;", false);
            methodVisitor.visitLdcInsn("  entry.getValue() ");
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            methodVisitor.visitVarInsn(ALOAD, 2);
            methodVisitor.visitMethodInsn(INVOKEINTERFACE, "java/util/Map$Entry", "getValue", "()Ljava/lang/Object;", true);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/Object;)Ljava/lang/StringBuilder;", false);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            methodVisitor.visitJumpInsn(GOTO, label0);
            methodVisitor.visitLabel(label1);
            methodVisitor.visitFieldInsn(GETSTATIC, "com/baidu/spi/ServiceProvider", "sProviders", "Ljava/util/Map;");
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitMethodInsn(INVOKEINTERFACE, "java/util/Map", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", true);
            methodVisitor.visitTypeInsn(CHECKCAST, "java/util/concurrent/Callable");
            methodVisitor.visitMethodInsn(INVOKEINTERFACE, "java/util/concurrent/Callable", "call", "()Ljava/lang/Object;", true);
            methodVisitor.visitInsn(ARETURN);
            methodVisitor.visitMaxs(3, 3);
            methodVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_STATIC, "<clinit>", "()V", null, null);
            methodVisitor.visitCode();
            methodVisitor.visitTypeInsn(NEW, "java/util/LinkedHashMap");
            methodVisitor.visitInsn(DUP);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/util/LinkedHashMap", "<init>", "()V", false);
            methodVisitor.visitFieldInsn(PUTSTATIC, "com/baidu/spi/ServiceProvider", "sProviders", "Ljava/util/Map;");

            methodVisitor.visitLdcInsn(Type.getType("Lcom/baidu/spi/Apple;"));
            methodVisitor.visitTypeInsn(NEW, "com/baidu/spi/AppleCallable");
            methodVisitor.visitInsn(DUP);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "com/baidu/spi/AppleCallable", "<init>", "()V", false);
            methodVisitor.visitMethodInsn(INVOKESTATIC, "com/baidu/spi/ServiceProvider", "register", "(Ljava/lang/Class;Ljava/util/concurrent/Callable;)V", false);
            methodVisitor.visitLdcInsn(Type.getType("Lcom/baidu/spi/Banana;"));
            methodVisitor.visitTypeInsn(NEW, "com/baidu/spi/BananaCallable");
            methodVisitor.visitInsn(DUP);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "com/baidu/spi/BananaCallable", "<init>", "()V", false);
            methodVisitor.visitMethodInsn(INVOKESTATIC, "com/baidu/spi/ServiceProvider", "register", "(Ljava/lang/Class;Ljava/util/concurrent/Callable;)V", false);
            methodVisitor.visitInsn(RETURN);
            methodVisitor.visitMaxs(3, 0);
            methodVisitor.visitEnd();
        }
        classWriter.visitEnd();

        return classWriter.toByteArray();
    }
}