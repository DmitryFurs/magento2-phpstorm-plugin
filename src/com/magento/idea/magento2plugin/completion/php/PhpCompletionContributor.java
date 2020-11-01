package com.magento.idea.magento2plugin.completion.php;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.patterns.PlatformPatterns;
import com.jetbrains.php.lang.lexer.PhpTokenTypes;
import com.jetbrains.php.lang.psi.elements.PhpPsiElement;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import com.jetbrains.php.lang.psi.elements.impl.ArrayHashElementImpl;
import com.magento.idea.magento2plugin.completion.provider.ModuleNameCompletionProvider;
import static com.intellij.patterns.PlatformPatterns.psiElement;
import static com.intellij.patterns.StandardPatterns.string;

public class PhpCompletionContributor extends CompletionContributor {
    public PhpCompletionContributor() {

        /* Module name in app/etc/config.php */
        extend(
                CompletionType.BASIC,
                psiElement(PhpTokenTypes.STRING_LITERAL_SINGLE_QUOTE)
                    .withSuperParent(6,
                        PlatformPatterns.psiElement(ArrayHashElementImpl.class)
                            .withChild(PlatformPatterns.psiElement(PhpPsiElement.class)
                                .withChild(PlatformPatterns.psiElement(
                                    StringLiteralExpression.class
                                ).withText(string().contains("modules"))
                            )
                        )
                    ),
                new ModuleNameCompletionProvider()
        );
    }
}
