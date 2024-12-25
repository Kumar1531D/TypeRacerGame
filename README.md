# Typeracer Game

A competitive multiplayer typing game where players race to type the given text as quickly and accurately as possible. The game allows real-time progress updates and is designed to enhance typing speed and accuracy.

## Features

- **Multiplayer Rooms**: Players can join specific rooms using passwords.
- **Real-Time Updates**: Displays Words Per Minute (WPM) and progress for all players in the room in real-time.
- **Game Management**: The game starts automatically when all players in the room are ready.
- **Secure Backend**: Built with a Tomcat server to handle WebSocket connections and HTTP requests efficiently.

## Technology Stack

- **Backend**:
  - Java with Tomcat server
  - WebSocket for real-time communication
  - HTTP for additional server interactions
- **Frontend**:
  - HTML, CSS, and JavaScript for the user interface
  - WebSocket API for client-server communication

## Setup and Installation

1. Clone the repository:
   ```bash
   git clone <repository_url>
   cd typeracer-game
   ```

2. Set up the server:
   - Ensure you have Java installed.
   - Deploy the provided WAR file to your Tomcat server.

3. Start the Tomcat server:
   ```bash
   ./bin/startup.sh
   ```

4. Open the game in your browser:
   Navigate to `http://localhost:8000/login.html`

## How to Play

1. Join or create a game room by entering a room password.
2. Wait for all players to be ready.
3. Start typing the provided text as quickly and accurately as possible.
4. Track your WPM and progress in real-time.

## File Structure

- **/src**: Contains server-side code and WebSocket handling.
- **/webapp**: Includes frontend assets (HTML, CSS, JS).
- **/WEB-INF**: Deployment configuration for the Tomcat server.

## Future Enhancements

- Add support for custom text input by room creators.
- Implement rankings and leaderboards.
- Enable spectating for non-participating users.

## Contributions

Contributions are welcome! Feel free to submit pull requests or raise issues to improve the game.

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.
