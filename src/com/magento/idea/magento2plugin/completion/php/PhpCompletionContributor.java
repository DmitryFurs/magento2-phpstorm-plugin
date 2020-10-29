package com.magento.idea.magento2plugin.completion.php;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.patterns.PlatformPatterns;
import com.jetbrains.php.lang.lexer.PhpTokenTypes;
import com.magento.idea.magento2plugin.completion.provider.ModuleNameCompletionProvider;

public class PhpCompletionContributor extends CompletionContributor {
    public PhpCompletionContributor() {
        /* TODO: Works for any single quoted string in any PHP file */
        /* Module name in app/etc/config.php */
        extend(
                CompletionType.BASIC,
                PlatformPatterns.psiElement(PhpTokenTypes.STRING_LITERAL_SINGLE_QUOTE),
                new ModuleNameCompletionProvider()
        );
    }
}
