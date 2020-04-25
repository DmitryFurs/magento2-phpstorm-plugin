/*
 * Copyright © Magento, Inc. All rights reserved.
 * See COPYING.txt for license details.
 */
package com.magento.idea.magento2plugin.util.magento;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.jetbrains.php.lang.psi.PhpFile;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import com.magento.idea.magento2plugin.magento.files.RegistrationPhp;
import org.jetbrains.annotations.Nullable;

public class GetComponentNameByDirectory {
    private static GetComponentNameByDirectory INSTANCE = null;
    private Project project;

    public static GetComponentNameByDirectory getInstance(Project project) {
        if (null == INSTANCE) {
            INSTANCE = new GetComponentNameByDirectory();
        }
        INSTANCE.project = project;
        return INSTANCE;
    }

    public String execute(PsiDirectory psiDirectory) {
        PhpFile registrationPhp = getRegistrationPhpRecursively(psiDirectory, project);
        if (registrationPhp == null) {
            return null;
        }
        PsiElement[] childElements = registrationPhp.getChildren();
        if (childElements.length == 0) {
            return null;
        }
        return getName(childElements);
    }


    private PhpFile getRegistrationPhpRecursively(PsiDirectory psiDirectory, Project project) {
        PsiElement[] containingFiles = psiDirectory.getChildren();
        PhpFile containingFile = getModuleRegistrationPhpFile(containingFiles);
        if (containingFile != null) {
            return containingFile;
        }
        String basePath = project.getBasePath();
        if (psiDirectory.getVirtualFile().getPath().equals(basePath)) {
            return null;
        }
        PsiDirectory getParent = psiDirectory.getParent();
        if (getParent != null) {
            return getRegistrationPhpRecursively(getParent, project);
        }
        return null;
    }

    private String getName(PsiElement[] childElements) {
        MethodReference[] methods = parseRegistrationPhpElements(childElements);
        for (MethodReference method: methods) {
            if (!method.getName().equals(RegistrationPhp.REGISTER_METHOD_NAME)) {
                continue;
            }
            PsiElement[] parameters =  method.getParameters();
            for (PsiElement parameter: parameters) {
                if (!(parameter instanceof StringLiteralExpression)) {
                    continue;
                }
                return ((StringLiteralExpression) parameter).getContents();
            }
        }
        return null;
    }

    public MethodReference[] parseRegistrationPhpElements(PsiElement[] elements) {
        for (PsiElement element: elements) {
            MethodReference[] methods = PsiTreeUtil.getChildrenOfType(element, MethodReference.class);
            if (methods == null) {
                PsiElement[] children = element.getChildren();
                methods = parseRegistrationPhpElements(children);
            }
            if (methods != null) {
                return methods;
            }
        }
        return null;
    }

    @Nullable
    private PhpFile getModuleRegistrationPhpFile(PsiElement[] containingFiles) {
        if (containingFiles.length != 0) {
            for (PsiElement containingFile: containingFiles) {
                if (!(containingFile instanceof PhpFile)) {
                    continue;
                }
                String filename = ((PhpFile) containingFile).getName();
                if (!filename.equals(RegistrationPhp.FILE_NAME)) {
                    continue;
                }
                return (PhpFile) containingFile;
            }
        }
        return null;
    }
}
