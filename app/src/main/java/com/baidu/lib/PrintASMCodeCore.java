package com.baidu.lib;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.util.ASMifier;
import org.objectweb.asm.util.Printer;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Print asm core code.
 */
public class PrintASMCodeCore {
    public static void main(){
        try {
            readClass(MyCallable.class.getName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param className 接受.分割符分割的类名
     */
    public static void readClass(String className) throws IOException {
        // (1) 设置参数
        int parsingOptions = ClassReader.SKIP_FRAMES | ClassReader.SKIP_DEBUG;
        // (2) 打印结果
        Printer printer = new ASMifier();
        PrintWriter printWriter = new PrintWriter(System.out, true);
        TraceClassVisitor traceClassVisitor = new TraceClassVisitor(null, printer, printWriter);
        new ClassReader(className).accept(traceClassVisitor, parsingOptions);
    }
}
