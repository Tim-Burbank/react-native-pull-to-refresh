import React, {Component} from 'react';
import {requireNativeComponent, ViewPropTypes} from 'react-native';
import PropTypes from 'prop-types';

const RefreshLayout = requireNativeComponent('RTCDefaultRefreshViewManager', AndroidRefreshLayout)

export class AndroidRefreshLayout extends Component {

    static propTypes = {
        onRefresh: PropTypes.func,
        refreshing: PropTypes.bool,
        ...ViewPropTypes
    }
}
export default RefreshLayout
