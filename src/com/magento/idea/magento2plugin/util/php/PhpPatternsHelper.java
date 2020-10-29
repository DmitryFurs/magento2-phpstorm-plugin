/**
 * Copyright © Magento, Inc. All rights reserved.
 * See COPYING.txt for license details.
 */

package com.magento.idea.magento2plugin.util.php;

import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.PhpLanguage;
import com.jetbrains.php.lang.patterns.PhpPatterns;
import com.jetbrains.php.lang.psi.elements.ParameterList;
import com.jetbrains.php.lang.psi.elements.impl.ArrayHashElementImpl;
import com.jetbrains.php.lang.psi.elements.impl.PhpPsiElementImpl;
import com.jetbrains.php.lang.psi.elements.impl.StringLiteralExpressionImpl;

import static com.intellij.patterns.StandardPatterns.string;

public class PhpPatternsHelper {
    public static final ElementPattern<? extends PsiElement> STRING_METHOD_ARGUMENT =
        PhpPatterns
            .phpLiteralExpression()
            .withParent(
                PlatformPatterns
                    .psiElement(ParameterList.class)
                    .withParent(
                        PhpPatterns
                            .phpFunctionReference()
                    )
            ).withLanguage(PhpLanguage.INSTANCE);

    public static final ElementPattern<? extends PsiElement> CONFIGPHP_MODULENAME =
        PlatformPatterns.psiElement(StringLiteralExpressionImpl.class)
            .withSuperParent(5,PlatformPatterns.psiElement(ArrayHashElementImpl.class)
                .withChild(PlatformPatterns.psiElement(PhpPsiElementImpl.class)
                    .withChild(PlatformPatterns.psiElement(StringLiteralExpressionImpl.class)
                        .withText(string().contains("modules"))
                    )
                )
            );
}
