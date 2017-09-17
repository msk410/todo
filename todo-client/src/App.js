import React, { Component } from 'react';

import './App.css';
import 'font-awesome/css/font-awesome.css';


class App extends Component {
  constructor(props) {
    super(props)
    this.state = {
      todoList: [],
    }
  }  
  async componentDidMount() {
    console.log("hey")
    try {
      const response = await fetch(`http://localhost:8080/list`, {
        method: "GET",  
      });
      let data = await response.json();
      let newTodo = [];
      data.map((elem,index) => {
        newTodo.push(elem);
      })
      this.setState({
        todoList: newTodo,
      })
    }
    catch(err) {
    console.log(err);
    }
  }

  test(e) {
    fetch("http://localhost:8080", {
      method: "POST",
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
    
      //make sure to serialize your JSON body
      body: JSON.stringify({
        message: e.target.message.value,
      })
    })
  }

  
  render() {
    return (
      <div>

        <ul style={{listStyle:"none"}}>
          {this.state.todoList.map((elem, index) => {
            return <li><input type="checkbox" checked = {elem.finished}/>{elem.message}</li>
          })}
        </ul>
        <form onSubmit={this.test.bind(this)}>
          <input type="text" name="message" placeholder="add a new todo" />
        </form>
      </div>
      
    )
  }
}

export default App;
