import React from 'react';
import CSSModules from 'react-css-modules';
import styles from './TextField.module.css';

const TextField = (props) => {
  function onChange(e){
    props.changeValue(e.target.value)
  }
  return (
    <input styleName="text-field" id="y-text" type="number" step={0.001}
      value={props.value}
           onChange={onChange}
      maxLength={props.maxLength} placeholder={props.placeholder} />
  );
}

export default CSSModules(TextField, styles, { allowMultiple: true, handleNotFoundStyleName: 'ignore' });
