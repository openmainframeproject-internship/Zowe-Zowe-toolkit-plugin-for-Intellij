package auxiliary

import com.intellij.remoterobot.RemoteRobot
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import auxiliary.*
import auxiliary.closable.ClosableFixtureCollector
import auxiliary.containers.*
import com.intellij.remoterobot.fixtures.ContainerFixture
import com.intellij.remoterobot.search.locators.Locator
import com.intellij.remoterobot.search.locators.byXpath
import com.intellij.remoterobot.utils.waitFor
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Tag
import java.time.Duration

/**
 * When adding UI tests to GitHub Actions pipeline, there is a need to first run dummy test, which
 * gets rid of tips and agrees to license agreement.
 */
@Tag("FirstTime")
@ExtendWith(RemoteRobotExtension::class)
class ConnectionManager {
    private var stack = mutableListOf<Locator>()
    private val projectName = "untitled"

    @Test
    fun firstTime(remoteRobot: RemoteRobot) = with(remoteRobot) {
        welcomeFrame {
            open(projectName)
        }
        ideFrameImpl(projectName, stack) {
            waitFor(Duration.ofMinutes(10)) {
                hasText("For Mainframe Plugin Privacy Policy and Terms and Conditions")
            }
            dialog("For Mainframe Plugin Privacy Policy and Terms and Conditions") {
                clickButton("I Agree")
            }
            dialog("Tip of the Day") {
                checkBox("Don't show tips").select()
                clickButton("Close")
            }
            clickButton(byXpath("//div[@accessiblename='Got It' and @class='JButton' and @text='Got It']"),
            Duration.ofSeconds(180))
            close()
        }
    }
}



