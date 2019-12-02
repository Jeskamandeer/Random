import React, {Component} from 'react';
import {
  BrowserRouter as Router,
  Route,
  Link,
  Switch
} from 'react-router-dom';
import './App.css';

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {data: []};
  }
  //Hakee tallenteut klubit talteen
  async componentDidMount() {
    let response = await fetch("/getClubs");
    let data = await response.json();
    this.setState({data})
    this.state.data = data;
  }

  render() {
    return (
      <Router>
        <Link to="/addClub">
          <button>Add Club To System</button>
        </Link>
        <Link to="/listAll">
          <button>List Clubs of System</button>
        </Link>

        <Switch>
          <Route path="/listAll">
            <ListAllPage data={this.state.data}/>
          </Route>
          <Route path="/addClub">
            <AddClubPage/>
          </Route>
        </Switch>

      </Router>
    );
  }
}
//Kaikki tallenetut klubit sivu
function ListAllPage(savedClubs) {
  return (
    <div>
      <h2>List Of All Clubs</h2>
      <ol>
        {savedClubs.data.map(club =>
          <li>{club.clubName}
            <ul>
              <li>{club.player1}</li>
              <li>{club.player2}</li>
              <li>{club.player3}</li>
              <li>{club.player4}</li>
              <li>{club.player5}</li>
            </ul>
          </li>)
        }
      </ol>
    </div>
  )
}
//Lisää uusi klubi sivu
function AddClubPage() {
  return (
    <div>
      <h2>Add New Club</h2>
      <form method="post" action="/createClub">
        <ul>
          <li>
            <label htmlFor="clubName">Club Name</label>
            <input
              type="text"
              name="clubName"
            />
          </li>
          <br/>
          <li>
            <label htmlFor="player1">Player 1</label>
            <input
              type="text"
              name="player1"
            />
          </li>
          <li>
            <label htmlFor="player2">Player 2</label>
            <input
              type="text"
              name="player2"
            />
          </li>
          <li>
            <label htmlFor="player3">Player 3</label>
            <input
              type="text"
              name="player3"
            />
          </li>
          <li>
            <label htmlFor="player4">Player 4</label>
            <input
              type="text"
              name="player4"
            />
          </li>
          <li>
            <label htmlFor="player5">Player 5</label>
            <input
              type="text"
              name="player5"
            />
          </li>
        </ul>
        <input
          type='submit'
        />
      </form>
    </div>
  )
}

export default App;
