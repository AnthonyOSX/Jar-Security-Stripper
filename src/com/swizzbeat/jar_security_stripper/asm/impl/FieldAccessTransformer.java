package com.swizzbeat.jar_security_stripper.asm.impl;

import com.swizzbeat.jar_security_stripper.asm.AbstractTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

/**
 * Created with IntelliJ IDEA
 * User: Anthony
 * Date: 12/20/14
 */

public class FieldAccessTransformer extends AbstractTransformer {

    @Override
    public void run(ClassNode node) {
        for (FieldNode fn : (Iterable<FieldNode>) node.fields) {
            if ((fn.access & Opcodes.ACC_FINAL) > 0) {
                //fn.access |= (~Opcodes.ACC_FINAL | Opcodes.ACC_PUBLIC);
                fn.access = fn.access & (~Opcodes.ACC_FINAL);
            }
        }
    }

}
