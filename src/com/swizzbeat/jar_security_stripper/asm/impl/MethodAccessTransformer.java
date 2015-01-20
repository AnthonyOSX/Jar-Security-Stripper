package com.swizzbeat.jar_security_stripper.asm.impl;

import com.swizzbeat.jar_security_stripper.asm.AbstractTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

/**
 * Created with IntelliJ IDEA
 * User: Anthony
 * Date: 12/20/14
 */

public class MethodAccessTransformer extends AbstractTransformer {

    @Override
    public void run(ClassNode node) {
        for (MethodNode mn : (Iterable<MethodNode>) node.methods) {
            if ((mn.access & Opcodes.ACC_FINAL) > 0) {
                //mn.access |= (~Opcodes.ACC_FINAL | Opcodes.ACC_PUBLIC);
                mn.access = mn.access & (~Opcodes.ACC_FINAL);
            }
        }
    }

}
