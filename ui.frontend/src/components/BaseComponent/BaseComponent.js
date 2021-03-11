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
 * Default Edit configuration for the Text component that interact with the Core Text component and sub-types
 *
 * @type EditConfig
 */
const BaseComponentEditConfig = {
  emptyLabel: 'Component is empty',

  isEmpty: function(props) {
    return !props || !props.name || props.name.trim().length < 1;
  }
};

/**
 * Text React component
 */
class BaseComponent extends Component {
  constructor(props) {
    super(props);

    this.state = {
      body: null,
    }
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
              __html: DOMPurify.sanitize(this.state.body)
            }}
        />
    );
  }
}

export default MapTo(['foundation/components/form/address', 'foundation/components/form/submit'])(
  BaseComponent,
  BaseComponentEditConfig
);
