import PropTypes from 'prop-types';
import React from 'react';
import {requireNativeComponent, NativeModules} from 'react-native';

var TangramRefreshControl = requireNativeComponent('TangramRefreshControl', RefreshLayout);
const avaliableKeys = [
  'refreshWidth',
  'refreshHeight',
  'refreshX',
  'refreshY',
  'refreshTriggerDistance'
]
class RefreshLayout extends React.Component {
  render() {
    return <TangramRefreshControl {...this.props} />;
  }
}

RefreshLayout.propTypes = {
  refreshing: PropTypes.bool,
  onRefresh: PropTypes.func,
};

module.exports = RefreshLayout;