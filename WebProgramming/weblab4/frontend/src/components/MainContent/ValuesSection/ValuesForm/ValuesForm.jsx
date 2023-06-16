import React, {useState, useEffect} from 'react';
import CSSModules from 'react-css-modules';
import styles from './ValuesForm.module.css';
import InfoMessage from './InfoMessage/InfoMessage';
import TextField from './TextField/TextField';
import GraphSection from "../../GraphSection/GraphSection";
import {checkEntry} from "../../../../redux/modules/values";
import {Button, Slider, ToggleButton, ToggleButtonGroup} from "@mui/material";

const CHECK = 'check';
const CLEAR = 'clear';

const validateForm = values => {
  let isNumeric = num => {
    return !isNaN(parseFloat(num)) && isFinite(num);
  }

  if (!isNumeric(values.rCurrent) || !values.rValues.includes(parseFloat(values.rCurrent))) {
    return 'Выберите значение R!';
  }

  if (!isNumeric(values.xCurrent) || !values.xValues.includes(parseFloat(values.xCurrent))) {
    values.xCurrent = document.getElementById('slider').value
  }

  if (!isNumeric(values.yCurrent) || values.yCurrent < values.yMin || values.yCurrent > values.yMax) {
    return `Введите значение Y от ${values.yMin} до ${values.yMax}!`;
  }

  return '';
}

const ValuesForm = (props) => {
  const [infoMessage, setInfoMessage] = useState('Введите координаты точки');
  const [action, setAction] = useState(undefined);

  const handleCheckCLick = () => {
    setAction(CHECK);
  }

  const handleClearCLick = () => {
    setAction(CLEAR);
  }

  const handleSubmit = (e) => {
    e.preventDefault();

    switch (action) {
      case CHECK:
        let message = validateForm(props);
        if (message === '') {
          props.checkEntry();
        }
        break;
      case CLEAR:
        props.clearEntries();
        break;
      default:
        alert('Неверный Action в ValuesForm!');
    }
  }

  useEffect(() => {
    let message = validateForm(props);
    setInfoMessage(message === '' ? 'Введите координаты точки' : message);
  }, [props]);

  return (
    <div>
      <GraphSection check={checkEntry}/>
      <form styleName="values-form" onSubmit={(e) => handleSubmit(e)}>
        <InfoMessage message={infoMessage}/>

        <div styleName="values-form__container">
          <label styleName="values-form__label" className="theme">
          <span className="values-form__label-text">
            R
          </span>
          </label>
          <div styleName="values-form__control">
            <ToggleButtonGroup fullWidth size={'large'} color="primary" exclusive value={props.rCurrent} onChange={props.selectR}>
              <ToggleButton value="1">1</ToggleButton>
              <ToggleButton value="2">2</ToggleButton>
              <ToggleButton value="3">3</ToggleButton>
              <ToggleButton value="4">4</ToggleButton>
              <ToggleButton value="5">5</ToggleButton>
            </ToggleButtonGroup>
          </div>
        </div>

        <div styleName="values-form__container">
          <label styleName="values-form__label" className="theme">
          <span className="values-form__label-text">
            X
          </span>
          </label>
          <div styleName="values-form__control">
            <Slider id="slider" defaultValue={0}
                    valueLabelDisplay="auto"
                    step={0.5}
                    marks
                    size="large"
                    min={-3}
                    max={5}
                    onChange={props.selectX}
            />
          </div>
        </div>

        <div styleName="values-form__container">
          <label styleName="values-form__label" htmlFor="y-text" className="theme">
          <span className="values-form__label-text">
            Y
          </span>
          </label>
          <div styleName="values-form__control">
            <TextField value={props.yCurrent} changeValue={props.changeY} maxLength="7" placeholder="Число от -5 до 5"/>
          </div>
        </div>

        <div styleName="values-form__control-container">
          <Button variant="contained" color="success" size="small" type="submit"
                  onClick={handleCheckCLick}>Проверить</Button>
          <Button variant="contained" color="secondary" size="small" type="submit"
                  onClick={handleClearCLick}>Очистить</Button>
        </div>
      </form>
    </div>
  );
}

export default CSSModules(ValuesForm, styles, {allowMultiple: true, handleNotFoundStyleName: 'ignore'});
