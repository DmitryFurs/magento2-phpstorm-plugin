/**
 * Copyright © Magento, Inc. All rights reserved.
 * See COPYING.txt for license details.
 */
package com.magento.idea.magento2plugin.reference.php;

import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceRegistrar;
import com.magento.idea.magento2plugin.reference.provider.ModuleNameReferenceProvider;
import com.magento.idea.magento2plugin.util.php.PhpPatternsHelper;
import com.magento.idea.magento2plugin.reference.provider.EventDispatchReferenceProvider;
import org.jetbrains.annotations.NotNull;

public class PhpReferenceContributor extends PsiReferenceContributor {
    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        // ->dispatch("event_name")
        registrar.registerReferenceProvider(
                PhpPatternsHelper.STRING_METHOD_ARGUMENT,
                new EventDispatchReferenceProvider()
        );

        // 'Vendor_Module' => 1
        registrar.registerReferenceProvider(
                PhpPatternsHelper.CONFIGPHP_MODULE,
                new ModuleNameReferenceProvider()
        );
    }
}
