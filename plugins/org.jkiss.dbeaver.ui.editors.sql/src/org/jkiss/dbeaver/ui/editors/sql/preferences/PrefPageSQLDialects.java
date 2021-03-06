/*
 * DBeaver - Universal Database Manager
 * Copyright (C) 2010-2019 Serge Rider (serge@jkiss.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jkiss.dbeaver.ui.editors.sql.preferences;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbenchPropertyPage;
import org.jkiss.dbeaver.Log;
import org.jkiss.dbeaver.model.preferences.DBPPreferenceStore;
import org.jkiss.dbeaver.model.sql.registry.SQLDialectDescriptor;
import org.jkiss.dbeaver.model.sql.registry.SQLDialectRegistry;
import org.jkiss.dbeaver.runtime.DBWorkbench;
import org.jkiss.dbeaver.ui.DBeaverIcons;
import org.jkiss.dbeaver.ui.UIUtils;
import org.jkiss.dbeaver.ui.editors.sql.internal.SQLEditorMessages;
import org.jkiss.dbeaver.ui.preferences.AbstractPrefPage;
import org.jkiss.dbeaver.utils.PrefUtils;

import java.util.Comparator;
import java.util.List;

/**
 * PrefPageSQLDialects
 */
public class PrefPageSQLDialects extends AbstractPrefPage implements IWorkbenchPreferencePage, IWorkbenchPropertyPage
{
    public static final String PAGE_ID = "org.jkiss.dbeaver.preferences.main.sql.dialects"; //$NON-NLS-1$

    private static final Log log = Log.getLog(PrefPageSQLDialects.class);
    private IAdaptable element;

    public PrefPageSQLDialects()
    {
        super();
    }

    @Override
    protected Control createContents(Composite parent) {
        boolean isPrefPage = element == null;

        Composite composite = UIUtils.createComposite(parent, isPrefPage ? 2 : 1);

        if (isPrefPage) {
            // Create dialect selector
            Composite dialectsGroup = UIUtils.createComposite(composite, 1);
            dialectsGroup.setLayoutData(new GridData(GridData.FILL_VERTICAL | GridData.HORIZONTAL_ALIGN_BEGINNING));
            UIUtils.createControlLabel(dialectsGroup, "Dialects", 2);

            Tree dialectTable = new Tree(dialectsGroup, SWT.BORDER | SWT.SINGLE);
            dialectTable.setLayoutData(new GridData(GridData.FILL_BOTH));

            List<SQLDialectDescriptor> dialects = SQLDialectRegistry.getInstance().getDialects();
            dialects.sort(Comparator.comparing(SQLDialectDescriptor::getLabel));
            for (SQLDialectDescriptor dialect : dialects) {
                TreeItem di = new TreeItem(dialectTable, SWT.NONE);
                di.setText(dialect.getLabel());
                di.setImage(DBeaverIcons.getImage(dialect.getIcon()));
            }
        }

        {
            Composite settingsGroup = UIUtils.createComposite(composite, 2);
            settingsGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_BEGINNING));
            UIUtils.createControlLabel(settingsGroup, SQLEditorMessages.pref_page_sql_format_label_settings, 2);

            UIUtils.createLabelText(settingsGroup, "Keywords", "", SWT.BORDER);
            UIUtils.createLabelText(settingsGroup, "Data Types", "", SWT.BORDER);

        }

        performDefaults();

        return composite;
    }

    @Override
    protected void performDefaults()
    {
        DBPPreferenceStore store = DBWorkbench.getPlatform().getPreferenceStore();


        super.performDefaults();
    }

    @Override
    public boolean performOk()
    {
        DBPPreferenceStore store = DBWorkbench.getPlatform().getPreferenceStore();

        //store.setValue(SQLPreferenceConstants.SCRIPT_BIND_EMBEDDED_READ, bindEmbeddedReadCheck.getSelection());

        PrefUtils.savePreferenceStore(store);

        return super.performOk();
    }

    @Override
    public void init(IWorkbench workbench) {

    }

    @Override
    public IAdaptable getElement() {
        return element;
    }

    @Override
    public void setElement(IAdaptable element) {
        this.element = element;
    }

}