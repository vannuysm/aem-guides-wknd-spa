/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 ~ Copyright 2020 Adobe Systems Incorporated
 ~
 ~ Licensed under the Apache License, Version 2.0 (the "License");
 ~ you may not use this file except in compliance with the License.
 ~ You may obtain a copy of the License at
 ~
 ~     http://www.apache.org/licenses/LICENSE-2.0
 ~
 ~ Unless required by applicable law or agreed to in writing, software
 ~ distributed under the License is distributed on an "AS IS" BASIS,
 ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 ~ See the License for the specific language governing permissions and
 ~ limitations under the License.
 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

import { MapTo } from '@adobe/aem-react-editable-components';
import DOMPurify from 'dompurify';
import React, { Component } from 'react';
import extractModelId from '../../utils/extract-model-id';

/**
 * Default Edit configuration for the Base component that interact with Base components and sub-types
 *
 * @type EditConfig
 */
const BaseEditConfig = {
    emptyLabel: 'Configure Me',

    isEmpty: function (props) {
        return !props || Object.keys(props).length <=4;
    },
};

/**
 * React component that renders a Base component
 */
class Base extends Component {
    constructor(props) {
        super(props);

        this.state = {
            body: null,
        };
    }

    componentDidMount() {
        (async () => {
            const response = await fetch(`${this.props.cqPath}.html`);
            const body = await response.text();
            this.setState({ body });
        })();
    }

    render() {
        return (
            <div
                id={extractModelId(this.props.cqPath)}
                data-rte-editelement
                dangerouslySetInnerHTML={{
                    __html: DOMPurify.sanitize(this.state.body),
                }}
            />
        );
    }
}

export default MapTo([
    'base-app/components/content/accordionSlice',
    'base-app/components/content/alert',
    'base-app/components/content/assesmentDetails',
    'base-app/components/content/assesmentSummary',
    'base-app/components/content/autoPay',
    'base-app/components/content/banner',
    'base-app/components/content/breadcrumb',
    'base-app/components/content/cardComponent',
    'base-app/components/content/carousel',
    'base-app/components/content/cellComponent',
    'base-app/components/content/checkList',
    'base-app/components/content/column',
    'base-app/components/content/content',
    'base-app/components/content/contentSlice',
    'base-app/components/content/countdownTimer',
    'base-app/components/content/ctaSlice',
    'base-app/components/content/customForm',
    'base-app/components/content/design',
    'base-app/components/content/dynamic-ctaSlice',
    'base-app/components/content/dynamicBanner',
    'base-app/components/content/form',
    'base-app/components/content/genericFooter',
    'base-app/components/content/genericHeader',
    'base-app/components/content/glossary',
    'base-app/components/content/horizontalRule',
    'base-app/components/content/iconList',
    'base-app/components/content/imageSlice',
    'base-app/components/content/indexCardComponent',
    'base-app/components/content/leadform',
    'base-app/components/content/rmCategoryComponent',
    'base-app/components/content/rmconfigurationcomponent',
    'base-app/components/content/savingspointCalculator',
    'base-app/components/content/search',
    'base-app/components/content/shareSlice',
    'base-app/components/content/stockTicker',
    'base-app/components/content/tableComponent',
    'base-app/components/content/tabSlice',
    'base-app/components/content/videoComponent',
])(
    Base,
    BaseEditConfig,
);
