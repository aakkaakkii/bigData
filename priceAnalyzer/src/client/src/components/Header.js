import {AppBar, Box, Button, Container, IconButton, Menu, MenuItem, Toolbar, Typography} from "@mui/material";
import {Link} from "react-router-dom";

const Header = () => {
    return <AppBar position="static">
        <Container>
            <Toolbar disableGutters>
                <Box sx={{ flexGrow: 1, display: { xs: "none", md: "flex" } }}>
                    <Button
                        component={Link}
                        to={"/"}
                        sx={{ my: 2, color: "white", display: "block" }}
                    >
                        Main
                    </Button>
                </Box>

            </Toolbar>
        </Container>
    </AppBar>
}

export default Header;